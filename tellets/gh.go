package tellets
import (
	"regexp"
	"net/url"
	"github.com/google/go-github/github"
	"fmt"
	"golang.org/x/oauth2"
	"path"
	"github.com/op/go-logging"
)

var regGitHubSchema = regexp.MustCompile("^(?P<user>[^/]+)/(?P<repo>[^/]+)(/(?P<path>.*))?")



// tokenSource is an oauth2.TokenSource which returns a static access token
type tokenSource struct {
	token *oauth2.Token
}

// Token implements the oauth2.TokenSource interface
func (t *tokenSource) Token() (*oauth2.Token, error) {
	return t.token, nil
}

type ghClient struct {
	*github.Client
	cache map[string]interface{}
}
func (gh *ghClient)Tree(user, repo, sha string) ([]github.TreeEntry, error) {
	key := fmt.Sprintf("/tree/%v/%v/%v", user, repo, sha)
	if r := gh.cache[key]; r!= nil {
		return r.([]github.TreeEntry), nil
	}
	//TODO May exceed the max entries
	t, _, err := gh.Git.GetTree(user, repo, sha, true)
	if err != nil {return nil, err}
	gh.Cache(key, t.Entries)
	return t.Entries, nil
}

func (gh *ghClient)Repo(user, repo string) (*github.Repository, error) {
	key := fmt.Sprintf("%v/%v", user, repo)

	if r := gh.cache[key]; r!= nil {
		return r.(*github.Repository), nil
	}
	r, _, err := gh.Repositories.Get(user, repo)
	if err == nil {gh.Cache(key, r)}
	return r, err
}

func (gh *ghClient)Contents(user, repo, path string) ([]*github.RepositoryContent, error) {
	key := fmt.Sprintf("%v/%v/%v", user, repo, path)
	if r := gh.cache[key]; r!= nil {
		return r.([]*github.RepositoryContent), nil
	}
	one, many, _, err := gh.Repositories.GetContents(user, repo, path, nil)
	if err!= nil {return nil, err}

	var r []*github.RepositoryContent
	if one != nil {
		r = []*github.RepositoryContent{one}
	}else {
		r = many
	}
	gh.Cache(key, r)
	return r, nil
}
func (gh *ghClient)Cache(key string, v interface{}) {
	gh.cache[key] = v
	if log.IsEnabledFor(logging.DEBUG) {
		log.Debug("Cache %v -> %v", key, v)
	}
}
func (gh *ghClient)List(s string) ([]Entry, error) {
	u, err := url.Parse(s)
	if err!= nil {return nil, err}

	match := regGitHubSchema.FindStringSubmatch(u.Opaque)
	result := make(map[string]string)
	for i, name := range regGitHubSchema.SubexpNames() {
		if name == "" {continue}
		result[name] = match[i]
	}
	user, repo, p := result["user"], result["repo"], result["path"]

	if p != "" {
		items := make([]Entry, 0)
		contents, err := gh.Contents(user, repo, p)
		if err!= nil {return nil, err}
		for _, r := range contents {
			if *r.Type != "file" {continue}
			e := NewEntry()
			e.Filename = path.Base(*r.Path)
			e.EntrySource = fmt.Sprintf("github:%s/%s/%s", user, repo, *r.Path)
			e.Hash = *r.SHA
			e.Source = s
			items = append(items, e)
		}
		return items, nil
	}

	r, err := gh.Repo(user, repo)
	if err != nil {return nil, err}
	entries, err := gh.Tree(user, repo, *r.MasterBranch)
	if err != nil {return nil, err}

	items := make([]Entry, 0)
	for _, r := range entries {
		if *r.Type != "blob" {continue}
		e := NewEntry()
		e.Filename = path.Base(*r.Path)
		e.EntrySource = fmt.Sprintf("github:%s/%s/%s", user, repo, *r.Path)
		e.Hash = *r.SHA
		e.Source = s
		items = append(items, e)
	}

	return items, nil
}

func newGHHelper(token string) *ghClient {
	ts := &tokenSource{
		//		&oauth2.Token{AccessToken: tellets.T.Option()["github-token"]},
	}
	tc := oauth2.NewClient(oauth2.NoContext, ts)
	client := github.NewClient(tc)
	helper := &ghClient{client, make(map[string]interface{})}
	return helper
}