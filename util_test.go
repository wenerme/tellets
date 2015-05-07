package tellets
import "testing"
import (
	"github.com/stretchr/testify/assert"
	"fmt"
)
func TestOption(t *testing.T) {
	assert := assert.New(t)
	opt := CreateOption("a=b,c,x,,log=info,")
	fmt.Println(opt)
	assert.EqualValues("b", opt["a"])
	assert.EqualValues("true", opt["c"])
	assert.EqualValues("info", opt["log"])
	//	reflect.DeepEqual(opt, CreateOption(opt.ToString()))
	assert.EqualValues(opt, CreateOption(opt.ToString()))
}