package tellets
import "errors"

type storage interface {
	ByHash(string) *Meta
	//	ByLink(string) *Meta
	//	ByTags([]string) []*Meta
	//	ByCategories([]string) []*Meta

	// Not found return empty
	Content(hash string) (string)

	// Store or Update
	// Will make a copy of Meta
	Store(*Meta, string) error
	Remove(*Meta) error
}

var Storage storage = createInMemoryStorage()
func init() {

}

type inMemStorage struct {
	hash map[string]Meta
	link map[string]*Meta
	category map[string][]*Meta
	content map[string]string
}

func createInMemoryStorage() storage {
	s := &inMemStorage{}
	s.hash = make(map[string]Meta)
	s.content = make(map[string]string)
	return s
}

func (s *inMemStorage)Store(o *Meta, c string) error {
	m := *o
	s.hash[m.Hash] = m
	s.content[m.Hash] = c
	return nil
}

func (s *inMemStorage)Remove(o *Meta) error {
	return nil
}
func (s *inMemStorage)ByHash(hash string) *Meta {
	if m, ok := s.hash[hash]; ok {
		a := m
		return &a
	}
	return nil
}
var ErrorHashNotFound = errors.New("Hash not found")
func (s *inMemStorage)Content(hash string) (string) {
	return s.content[hash]
}