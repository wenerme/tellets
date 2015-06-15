package tellets
import (
	"testing"
	"github.com/stretchr/testify/assert"
)



func TestHook(t *testing.T) {
	assert := assert.New(t)
	_=assert

	h := NewHook("hook")

	init := HookType("init")

	h.HookBefore(init, func(r *string) {
		*r += "1"
	})
	h.HookAfter(init, func(r *string) {
		*r += "10"
	})
	c := NewContext("hook")
	r := ""
	c.Add(&r)
	h.DoHook(init, c)
	assert.EqualValues("110", r)

	ch := CreateChildHook(h, "child")
	ch.HookBefore(init, func(r *string) {
		*r += "c1"
	})
	ch.HookBefore(init, func(r *string) {
		*r += "a1"
	})
	ch.HookAfter(init, func(r *string) {
		*r += "c10"
	})
	r = ""
	ch.DoHook(init, c)
	assert.EqualValues("a1c1110c10", r)
}
