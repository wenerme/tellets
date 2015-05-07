package tellets


import (
	"bytes"
	"io/ioutil"
	"gopkg.in/yaml.v2"
	"github.com/op/go-logging"
	"os"
)
var log = logging.MustGetLogger("tellets")

const (
	Version = "0.3.0"
)

var Tellets = &tellets{}
type t interface {
	Option() Option
	Author() *Author
	DataSource() []string
	Reload()
}
type telletsCfg struct {
	DataSource []string "data-source"
	Option Option
	Author *Author
}
type tellets struct {
	DataSource []string "data-source"
	Option Option
	Author *Author
}
type Author struct {
	Name string
	Email string
	Website string
}
func (a Author)ToString() string {
	buf := bytes.NewBufferString("")
	if a.Name != "" {buf.WriteString(a.Name)}
	if a.Email != "" {buf.WriteString("<"+a.Email+">")}
	if a.Website != "" {buf.WriteString("("+a.Name+")")}
	return buf.String()
}

func init() {
	//	format := logging.MustStringFormatter("%{color}%{time:15:04:05} %{level:.4s} %{shortfunc} %{color:reset} %{message}", )
	//		format := logging.MustStringFormatter("%{color}%{time:15:04:05.000} %{longfile} %{shortfunc} ▶ %{level:.4s} %{id:03x}%{color:reset} %{message}", )
	format := logging.MustStringFormatter("%{color}%{time:15:04:05} %{longfile} %{shortfunc} %{level:.4s} %{id:03x}%{color:reset} %{message}", )
	backend1 := logging.NewLogBackend(os.Stdout, "", 0)
	backend1Formatter := logging.NewBackendFormatter(backend1, format)
	logging.SetBackend(backend1Formatter)

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

	Tellets.Author = cfg.Author
	Tellets.Option = cfg.Option
	Tellets.DataSource = cfg.DataSource
}