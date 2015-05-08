package tellets


import (
	"bytes"
	"io/ioutil"
	"gopkg.in/yaml.v2"
	"github.com/op/go-logging"
	"os"
	"errors"
)
var log = logging.MustGetLogger("tellets")
func init() {
	//	format := logging.MustStringFormatter("%{color}%{time:15:04:05} %{level:.4s} %{shortfunc} %{color:reset} %{message}", )
	//		format := logging.MustStringFormatter("%{color}%{time:15:04:05.000} %{longfile} %{shortfunc} ▶ %{level:.4s} %{id:03x}%{color:reset} %{message}", )
	format := logging.MustStringFormatter("%{color}%{time:15:04:05} %{longfile} %{shortfunc} %{level:.4s} %{id:03x}%{color:reset} %{message}", )
	backend1 := logging.NewLogBackend(os.Stdout, "", 0)
	backend1Formatter := logging.NewBackendFormatter(backend1, format)
	logging.SetBackend(backend1Formatter)
}
const (
	Version = "0.3.0"
)

var T = &tellets{}
type LifeCycle interface {
	Start() error
	Stop() error
	Name()
}
type telletsCfg struct {
	Description string
	ExternalLinks map[string]string "external-links"
	DataSource []string "data-source"
	Option Option
	Author *Author
}
type tellets struct {
	dataSource []string "data-source"
	option Option
	author *Author
	source chan Source
	hooks map[string][]func()
}
func (t *tellets)Option() Option {
	return t.option
}
func (t *tellets)Author() *Author {
	return t.author
}
func (t *tellets)Start() (err error) {
	defer func() {
		if r := recover(); r != nil {
			switch x := r.(type) {
				case string:
				err = errors.New(x)
				case error:
				err = x
				default:
				err = errors.New("Unknown panic")
			}
		}
	}()
	for _, f := range t.hooks["Start"] {
		f()
	}
	return
}


type Author struct {
	Name string `json:"name,omitempty"`
	Email string `json:"email,omitempty"`
	Website string `json:"website,omitempty"`
}

type Owner struct {
	Name string `json:"name,omitempty"`
	Email string `json:"email,omitempty"`
	Website string `json:"website,omitempty"`

}
func (a Author)ToString() string {
	buf := bytes.NewBufferString("")
	if a.Name != "" {buf.WriteString(a.Name)}
	if a.Email != "" {buf.WriteString("<"+a.Email+">")}
	if a.Website != "" {buf.WriteString("("+a.Name+")")}
	return buf.String()
}

func init() {
	// default read tellets.yaml
	b, err := ioutil.ReadFile("tellets.yaml")
	if err != nil {
		log.Warning("Read tellets config file failed: %s", err)
		return
	}
	cfg := &telletsCfg{}
	err = yaml.Unmarshal(b, cfg)
	if err != nil {
		log.Warning("Parse tellets config file failed: %s", err)
		return
	}
	log.Info("Load tellets config file %s", "tellets.yaml")

	T.author = cfg.Author
	T.option = cfg.Option
	T.dataSource = cfg.DataSource

	go func() {
		for {
			s := <-T.source
			m, err := MetaParserManager.Parse(s.Filename, s.Content)
			if err == NoParserCanParse {
				log.Debug("No parser can parse %s", s.Filename)
				continue
			}else if err != nil {
				log.Error("Parse %s faield: %s", s.Filename, err)
			}
			err = Storage.Store(m, s.Content)
			if err != nil {
				log.Error("Store %s faield: %s", s.Filename, err)
			}
		}
	}()
}

type Source struct {
	Filename string
	Content string
	// Optional will generate by Hash
	Hash string
	// Optional may use to track
	Url string
}