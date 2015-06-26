package tellets
import (
	"net/url"
	"fmt"
	"github.com/google/go-github/github"
	"testing"
)

func TestGitHubHelper(t *testing.T) {
	u, err := url.Parse("github:wenerme/wener")
	if err != nil { panic(err) }
	fmt.Printf("%#v\n", u)
	gh := &ghClient{github.NewClient(nil), make(map[string]interface{})}
	entries, err := gh.List("github:wenerme/wener//")
	if err != nil { panic(err) }
	for _, e := range entries {
		fmt.Printf("%#v\n", e)
	}
	entries, err = gh.List("github:wenerme/wener/articles/")
	if err != nil { panic(err) }
	for _, e := range entries {
		fmt.Printf("%#v\n", e)
	}
}