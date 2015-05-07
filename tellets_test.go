package tellets
import (
	"testing"
	"github.com/stretchr/testify/assert"
	"gopkg.in/yaml.v2"
	"io/ioutil"
)

func TestTelletsConfig(t *testing.T) {
	assert := assert.New(t)
	_=assert
	b, err := ioutil.ReadFile("tellets-example.yaml")
	if err != nil { panic(err)}
	tl := &tellets{}
	err = yaml.Unmarshal(b, tl)
	if err != nil { panic(err)}

	assert.EqualValues("git:wenerme/wener", tl.DataSource[0])
	assert.EqualValues("wener", tl.Author.Name)
	assert.EqualValues("wener@wener.me", tl.Author.Email)
	assert.EqualValues("http://hi.wener.me", tl.Author.Website)
	assert.EqualValues("7a2c0a914abd3d0835f76a9cf369db449ed596a4", tl.Option["github-token"])
}
