package tellets
import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestInMemoryStorage(t *testing.T) {
	assert := assert.New(t)
	_ = assert
	s := createInMemoryStorage()
	{
		m := &Meta{}
		m.Hash = "AAA"
		m.Title = "Title"
		s.Store(m, "Content")

		assert.EqualValues("Content", s.Content("AAA"))
		assert.EqualValues(m, s.ByHash("AAA"))
		m.Title = "YYY"
		assert.EqualValues("Title", s.ByHash("AAA").Title)
	}
}

