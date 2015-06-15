package tellets
import (
	"github.com/op/go-logging"
	"os"
	"gopkg.in/yaml.v2"
	"io/ioutil"
	"github.com/jinzhu/gorm"
	_ "github.com/mattn/go-sqlite3"
	"github.com/syndtr/goleveldb/leveldb/errors"
	"fmt"
)
const (
	Version = "0.4.0"
)

var log = logging.MustGetLogger("tellets")
func init() {
	//format := logging.MustStringFormatter("%{color}%{time:15:04:05} %{level:.4s} %{shortfunc} %{color:reset} %{message}", )
	//format := logging.MustStringFormatter("%{color}%{time:15:04:05.000} %{longfile} %{shortfunc} ▶ %{level:.4s} %{id:03x}%{color:reset} %{message}", )
	format := logging.MustStringFormatter("%{color}%{time:15:04:05} %{longfile} %{shortfunc} %{level:.4s} %{id:03x}%{color:reset} %{message}", )
	backend1 := logging.NewLogBackend(os.Stdout, "", 0)
	backend1Formatter := logging.NewBackendFormatter(backend1, format)
	logging.SetBackend(backend1Formatter)
}

type tellets struct {
	s Storage
	c Context
	h Hook
	o TelletsOption
}
func (t *tellets)Option() TelletsOption {
	return t.o
}
func (t *tellets)Hook() Hook {
	return t.h
}
func (t *tellets)Context() Context {
	return t.c
}
func (t *tellets)Storage() Storage {
	return t.s
}
func NewTellets(cfgFile string) Tellets {
	b, err := ioutil.ReadFile(cfgFile)
	if err != nil { panic(err)}
	o := TelletsOption{}
	err = yaml.Unmarshal(b, &o)
	if err != nil { panic(err)}
	return NewTelletsByOption(o)
}
func NewTelletsByOption(o TelletsOption) Tellets {
	ctx := NewContext("Tellets")
	t := &tellets{
		nil,
		ctx,
		NewHook("Tellets"),
		o,
	}
	t.s = NewStorage(t, o.Storage)
	ctx.Add(Tellets(t))
	ctx.Add(o)
	ctx.Add(t.Storage())
	log.Info("Create Tellets with %#v", o)
	// Init Plugins
	for _, f := range plugins {
		f(t)
	}
	return t
}
type storage struct {
	t  Tellets
	o  StorageOption
	db gorm.DB
}
func NewStorage(t Tellets, o StorageOption) Storage {
	db, err := gorm.Open(o.Dialect, o.Arguments...)
	if err != nil {panic(err)}
	s := &storage{t, o, db}
	log.Info("Create Storage with %#v", o)
	return s
}
func (s *storage)Store(v interface{}) error {
	switch v.(type){
		case *Meta:
		default:
		return errors.New(fmt.Sprintf("Can not store type %T with %#v", v, v))
	}
	return nil
}
func (s *storage)DB() gorm.DB {
	return s.db
}
func (s *storage)TableName(n string) string {
	return s.o.TableNamePrefix+n+s.o.TableNameSuffix
}