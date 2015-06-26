package main
import (
	"regexp"
	"io/ioutil"
	"fmt"
	"strings"
	"github.com/wenerme/tellets/tellets"
	"github.com/google/go-github/github"
	"golang.org/x/oauth2"
//	"gopkg.in/yaml.v2"
	"github.com/go-ini/ini"
	"flag"
)

var GlobalConfigFile = "tellets.global.ini"
var DefaultConfigFile = "tellets.ini"

func main() {
	_ = tellets.Version

	t6()
}
type Info struct{
	PackageName string
}

func t7() {
	i := &Info{}
	err := ini.MapToWithMapper(&i, ini.TitleUnderscore, []byte("packag_name=ini"))
	if err != nil { panic(err) }
	fmt.Printf("%#v\n",i)

}
func t6() {
	f, err := ini.Load(DefaultConfigFile, GlobalConfigFile)
	if err != nil { panic(err) }
	f.BlockMode=false

	flag.String("name", "foo", "a string")

	fmt.Println(f.Section("github").KeysHash()["token"])
	fmt.Println(f.Section("security").KeysHash()["cors"])
	fmt.Println(f.Section("database").KeysHash())


}
// tokenSource is an oauth2.TokenSource which returns a static access token
type tokenSource struct {
	token *oauth2.Token
}

// Token implements the oauth2.TokenSource interface
func (t *tokenSource) Token() (*oauth2.Token, error) {
	return t.token, nil
}

func t5() {
	ts := &tokenSource{
		//		&oauth2.Token{AccessToken: tellets.T.Option()["github-token"]},
	}

	tc := oauth2.NewClient(oauth2.NoContext, ts)

	client := github.NewClient(tc)

	// list all repositories for the authenticated user
	{
		l, _, err := client.RateLimits()
		if err != nil { panic(err)}
		fmt.Println(l)
	}
	{

	}
	{
		//		repo, _, err := client.Repositories.Get("wenerme", "wener")
		//		if err != nil { panic(err) }
		//		fmt.Println(repo)
	}

	{
		//		repo, _, err := client.Repositories.Get("wenerme", "wener")
		//		refs, _, err := client.Git.GetTree("wenerme", "wener", repo.MasterBranch, true)
		//		if err != nil { panic(err) }
		//		for _, r := range refs {
		//			fmt.Println(r)
		//		}
		//		client.Git.GetBlob()
	}
}

func t3() {
	m := make(map[string]string)
	m["a"]="b"
	fmt.Println("OUT", m["X"])
}

func matchToMap(r *regexp.Regexp, c string) (map[string]string, error) {
	m := make(map[string]string)
	match := r.FindAllStringSubmatch(c, -1)
	fmt.Println(match)
	for _, v := range match {
		result := make(map[string]string)
		for i, name := range r.SubexpNames() {
			result[name] = v[i]
		}
		if k, ok := result["key"]; ok {
			m[strings.TrimSpace(k)] = strings.TrimSpace(result["value"])
		}
	}
	return m, nil
}
func t2() {
	b, err := ioutil.ReadFile("README.md")
	if err != nil { panic(err)}
	c := string(b)
	//	fmt.Println(c)
	reg, err := regexp.Compile(`(?m)^\s*<!---*(?P<key>[^:]+):(?P<value>.*?)-*?-->\s*$`)
	regAll, err := regexp.Compile(`\A(\s*<!---*(?P<key>[^:]+):(?P<value>.*?)-*?-->)+`)
	if err != nil { panic(err)}

	m := reg.FindAllStringSubmatch(regAll.FindString(c), -1)

	for _, v := range m {
		fmt.Println(v[1], ":", v[2])
	}
	fmt.Println(regAll.FindString(c))
	fmt.Println(matchToMap(reg, regAll.FindString(c)))
}
func t1() {
	b, err := ioutil.ReadFile("README.md")
	if err != nil { panic(err)}
	c := string(b)
	reg, err := regexp.Compile(`(?m)^\s*<!---*\s*(?P<type>more|summary|paging)\s*-*?-->\s*$`)
	if err != nil { panic(err)}

	m := reg.FindAllStringSubmatch(c, -1)

	for _, v := range m {

		fmt.Println(v[1])
	}
}