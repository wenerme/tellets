package tellets
import (
	"testing"
	"github.com/stretchr/testify/assert"
	"bytes"
	"io"
	"reflect"
)


func TestContext(t *testing.T) {
	assert := assert.New(t)
	_=assert

	c := NewContext("test")
	assert.True(c.Add(1))
	assert.True(c.Add("2"))
	assert.True(c.Add(uint(3)))
	assert.False(c.Add(uint(4)))

	{
		v1 := 0
		v2 := ""
		v3 := uint(0)
		assert.True(c.Get(&v1))
		assert.True(c.Get(&v2))
		assert.True(c.Get(&v3))
		assert.EqualValues([]interface{}{1, "2", uint(3)}, []interface{}{v1, v2, v3})
	}

	values, err := c.Call(func(i int, ui uint, s string) (int, uint, string) {
		return i, ui, s
	})

	assert.EqualValues(nil, err)
	assert.EqualValues([]interface{}{1, uint(3), "2"}, values)
}

func TestContextAddType(t *testing.T) {
	assert := assert.New(t)
	_=assert

	c := NewContext("test")
	br := &bytes.Reader{}
	var r io.Reader
	var ctx Context

	c.Add(io.Reader(br))

	assert.True(c.Get(&ctx))
	assert.Equal(c, ctx)
	// Will not get the value, the inject type is *bytes.Reader
	assert.False(c.Get(&r))

	{
		c := CreateChildContext(c, "type")
		c.AddType(&bytes.Reader{}, reflect.TypeOf((*io.Reader)(nil)).Elem())
		assert.True(c.Get(&r))
		assert.False(c.Add(&bytes.Reader{}))
	}

	{
		c := CreateChildContext(c, "type")
		// easy to write
		c.AddType(&bytes.Reader{}, func() io.Reader {return nil})
		assert.True(c.Get(&r))
	}

	{
		c := CreateChildContext(c, "type")
		// inject from parent
		c.AddFunc(func(br *bytes.Reader) io.Reader {return br})
		assert.True(c.Get(&r))
		assert.Equal(br, r)
	}

	assert.Panics(func() {
		c.AddType(c, func() io.Reader {return nil})
	})
	assert.Panics(func() {
		c.AddType(c, func() Context {return nil})
	})
	assert.Panics(func() {
		c.AddFunc(func(br *bytes.Reader, a int) io.Reader {return br})

	})
}