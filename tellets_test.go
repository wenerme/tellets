package tellets
import (
	"github.com/stretchr/testify/assert"
	"testing"
	"fmt"
)

func TestTelletsConfig(t *testing.T) {
	assert := assert.New(t)
	_=assert
	ts := NewTellets("tellets.yaml")

	fmt.Printf("%+#v", ts.Option())
}
