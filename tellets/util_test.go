package tellets
import "testing"
import (
	"github.com/stretchr/testify/assert"
	"fmt"
)
func TestOption(t *testing.T) {
	assert := assert.New(t)
	opt := ParseOption("a=b,c,x,,log=info,i=123")
	fmt.Println(opt)
	assert.EqualValues("b", opt["a"])
	assert.EqualValues("true", opt["c"])
	assert.EqualValues("info", opt["log"])
	//	reflect.DeepEqual(opt, CreateOption(opt.ToString()))
	assert.EqualValues(opt, ParseOption(opt.ToString()))
	i := 0
	opt.Get(&i, "i")
	assert.EqualValues(123, i)
}