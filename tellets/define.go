package tellets


const (
	Version = "0.4.5"
)

const (
	HookInitTellets HookType = "TelletsInit"
	HookInitCollector = "CollectorInit"
	HookInitParser = "ParserInit"
	HookWebServer = "HookWebServer"
	HookParse = "Parse"
	HookCollect = "Collect"
)

type DataServer interface {
	Tellets() Tellets
	Hook() Hook
	Context() Context
}

type Initializable interface {
	// return init function
	Init() interface{}
}

// f is func(any injectable parameter)Collector
func RegisterCollector(f interface{}) {
	RegisterPlugin(func(t Tellets) {
		t.Hook().HookAfter(HookInitCollector, func(svr CollectServer, s *[]Collector) {
			p, err := svr.Context().Call(f)
			if err != nil { panic(err) }
			log.Debug("Add collector %T", p[0])
			*s = append(*s, p[0].(Collector))
		})
	})
}

// f is func(any injectable parameter)Parser
func RegisterParser(f interface{}) {
	RegisterPlugin(func(t Tellets) {
		t.Hook().HookAfter(HookInitParser, func(svr CollectServer, s *[]Parser) {
			p, err := svr.Context().Call(f)
			if err != nil { panic(err) }
			log.Debug("Add parser %T", p[0])
			*s = append(*s, p[0].(Parser))
		})
	})
}
var plugins = make([]func(Tellets), 0)
func RegisterPlugin(f func(Tellets)) {
	plugins = append(plugins, f)
}
