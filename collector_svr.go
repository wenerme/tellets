package tellets
import (
	"net/url"
)


type collectSvr struct {
	parsers    []Parser
	collectors []Collector
	t          Tellets
	c          Context
	h          Hook
}

func NewCollectServer(t Tellets) CollectServer {
	cs := &collectSvr{
		make([]Parser),
		make([]Collector),
		t,
		CreateChildContextWithName(t.Context(), "collector-server"),
		CreateChildHook(t.Hook(), "collector-server"),
	}
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
	for _, c := range cs.collectors {
		all, err = c.Collect(u)
		if err == ErrCanNotCollect { continue }
		return
	}
}
func (cs *collectSvr)Parse(u *Collection) (m *Meta, err error) {
	for _, c := range cs.parsers {
		m, err = c.Parse(u)
		if err == ErrCanNotParse { continue }
		return
	}
}
func (cs *collectSvr)Discover(v interface{}) {
	if v == nil {
		log.Warning("I can't discover nil")
		return
	}
	switch v.(type){
		case url.URL:
		v = &v
		fallthrough
		case *url.URL:
		all, err := cs.Collect(v.(*url.URL))
		if err != nil {log.Warning("Discover %v failed due to %v", v, err)}
		cs.Discover(all)
		case []Collection:
		v = &v
		fallthrough
		case *[]Collection:
		for _, c := range v.(*[]Collection) {
			cs.Discover(c)
		}
		case Collection:
		v = &v
		fallthrough
		case *Collection:
		m, err := cs.Parse(v.(*Collection))
		if err != nil {log.Warning("Parse %v failed due to %v", v, err)}
		cs.t.Storage().Store(m)
		default:
		log.Warning("I don't know how to discover this %#v", v)
	}
}