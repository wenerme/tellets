package tellets
import (
	"net/url"
	"errors"
	"reflect"
)

// Collector can not collect, will try next
var ErrCanNotCollect = errors.New("Can not collect")

type Collector interface {
	Collect(*url.URL) ([]Entry, error)
}

type CollectServer interface {
	Tellets() Tellets
	Hook() Hook
	Context() Context
	Collect(string) ([]Entry, error)
	Parse(*Entry) error
	// Discover url, collection or meta
	Discover(interface{})
}

type collectSvr struct {
	parsers    []Parser
	collectors []Collector
	t          Tellets
	c          Context
	h          Hook
	sources []string
}

func NewCollectServer(t Tellets) CollectServer {
	cs := &collectSvr{
		parsers:make([]Parser, 0),
		collectors:make([]Collector, 0),
		t:t,
		c:CreateChildContext(t.Context(), "collector-server"),
		h:CreateChildHook(t.Hook(), "collector-server"),
	}
	cs.Context().AddType(cs, reflect.TypeOf((*CollectServer)(nil)).Elem())
	cs.Context().Add(&cs.parsers)
	cs.Context().Add(&cs.collectors)
	cs.sources = t.Config().CollectSources()
	cs.Hook().DoHook(HookInitCollector, cs.Context())
	cs.Hook().DoHook(HookInitParser, cs.Context())
	return cs
}
func (cs *collectSvr)Hook() Hook {
	return cs.h
}
func (cs *collectSvr)Context() Context {
	return cs.c
}
func (cs *collectSvr)Tellets() Tellets {
	return cs.t
}
func (cs *collectSvr)Collect(s string) (all []Entry, err error) {
	u, err := url.Parse(s)
	if err!=nil { return nil, err }
	err = ErrCanNotCollect
	for _, c := range cs.collectors {
		all, err = c.Collect(u)
		if err == ErrCanNotCollect { continue }
		return
	}
	return
}
func (cs *collectSvr)Parse(u *Entry) (err error) {
	err = ErrCanNotParse
	for _, c := range cs.parsers {
		err = c.Parse(u)
		if err == ErrCanNotParse { continue }
		return
	}
	return
}
func (cs *collectSvr)Discover(v interface{}) {
	if v == nil {
		log.Warning("I can't discover nil")
		return
	}
	reSwitch:
	switch v.(type){
		case string:
		all, err := cs.Collect(v.(string))
		if err != nil {panic(err)}
		cs.Discover(all)
		case []Entry:
		v = &v
		goto reSwitch
		case *[]Entry:
		for _, c := range *v.(*[]Entry) {
			cs.Discover(c)
		}
		case Entry:
		v = &v
		goto reSwitch
		case *Entry:
		err := cs.Parse(v.(*Entry))
		if err != nil {log.Warning("Parse %v failed due to %v", v, err)}
	// FIXME
		default:
		log.Warning("I don't know how to discover this %#v", v)
	}
}

