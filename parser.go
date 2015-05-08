package tellets
import (
	"errors"
)

type MetaParser interface {
	Parse(string) (*Meta, error)
	CanParse(filename string) bool
	Name() string
}

var MetaParserManager = createMetaParserManager()
func createMetaParserManager() *metaParserManager {
	mgr := &metaParserManager{
		make(map[string]MetaParser),
	}

	return mgr
}
type metaParserManager struct {
	parsers map[string]MetaParser
}
func (mgr *metaParserManager)Register(p MetaParser) error {
	if p.Name() == "" {
		return errors.New("Parser must have a name")
	}
	if _, exists := mgr.parsers[p.Name()]; exists {
		return errors.New("Parser with same name already exists")
	}
	mgr.parsers[p.Name()] = p
	log.Info("Register parser %s", p.Name())
	return nil
}

func (mgr *metaParserManager)Parser(name string) MetaParser {
	return mgr.parsers[name]
}
var NoParserCanParse = errors.New("No parser can parse this")
func (mgr *metaParserManager)Parse(fn, content string) (m *Meta, err error) {
	for n, p := range mgr.parsers {
		if !p.CanParse(fn) {continue}
		m, err=p.Parse(content)
		if err == nil { return }
		log.Error("Use %s to parse %s failed: %s", n, fn, err)
	}
	if err == nil {
		err = NoParserCanParse
	}
	return
}
