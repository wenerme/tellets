package tellets
import (
	"net/url"
	"errors"
	"reflect"
)

// Collector can not collect, will try next
var ErrCanNotCollect = errors.New("Can not collect")

type Collector interface {
	Collect(*url.URL) ([]Collection, error)
}

type CollectServer interface {
	Tellets() Tellets
	Hook() Hook
	Context() Context
	Collect(*url.URL) ([]Collection, error)
	Parse(*Collection) (*Meta, error)
	// Discover url, collection or meta
	Discover(interface{})
}

type CollectSource struct {
	URL  string
	CRON string
}


type collectSvr struct {
	parsers    []Parser
	collectors []Collector
	t          Tellets
	c          Context
	h          Hook
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
	cs.Hook().DoHook(HookCollectorInit, cs.Context())
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
func (cs *collectSvr)Collect(u *url.URL) (all []Collection, err error) {
	err = ErrCanNotCollect
	for _, c := range cs.collectors {
		all, err = c.Collect(u)
		if err == ErrCanNotCollect { continue }
		return
	}
	return
}
func (cs *collectSvr)Parse(u *Collection) (m *Meta, err error) {
	err = ErrCanNotParse
	for _, c := range cs.parsers {
		m, err = c.Parse(u)
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
		case url.URL:
		v = &v
		goto reSwitch
		case *url.URL:
		all, err := cs.Collect(v.(*url.URL))
		if err != nil {log.Warning("Discover %v failed due to %v", v, err)}
		cs.Discover(all)
		case []Collection:
		v = &v
		goto reSwitch
		case *[]Collection:
		for _, c := range *v.(*[]Collection) {
			cs.Discover(c)
		}
		case Collection:
		v = &v
		goto reSwitch
		case *Collection:
		m, err := cs.Parse(v.(*Collection))
		if err != nil {log.Warning("Parse %v failed due to %v", v, err)}
		// FIXME
		_=m
		default:
		log.Warning("I don't know how to discover this %#v", v)
	}
}

