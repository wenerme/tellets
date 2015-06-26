package tellets
import (
	"testing"
	"github.com/stretchr/testify/assert"
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
