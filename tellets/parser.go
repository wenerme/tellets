package tellets
import "errors"



// Parser can not parse, will try next
var ErrCanNotParse = errors.New("Can not parse")

type Parser interface {
	Parse(*Collection) (*Meta, error)
}
