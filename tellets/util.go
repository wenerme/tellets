package tellets
import (
	"strings"
	"bytes"
	"fmt"
	"reflect"
	"encoding/json"
	"github.com/op/go-logging"
	"os"
)

var log = logging.MustGetLogger("tellets")
func init() {
	//format := logging.MustStringFormatter("%{color}%{time:15:04:05} %{level:.4s} %{shortfunc} %{color:reset} %{message}", )
	//format := logging.MustStringFormatter("%{color}%{time:15:04:05.000} %{longfile} %{shortfunc} ▶ %{level:.4s} %{id:03x}%{color:reset} %{message}", )
	format := logging.MustStringFormatter("%{color}%{time:15:04:05} %{longfile} %{shortfunc} %{level:.5s} %{id:03x}%{color:reset} %{message}", )
	backend1 := logging.NewLogBackend(os.Stdout, "", 0)
	backend1Formatter := logging.NewBackendFormatter(backend1, format)
	logging.SetBackend(backend1Formatter)
}

type Option map[string]string

func ParseOption(s string) Option {
	m := make(map[string]string)
	for _, v := range strings.Split(s, ",") {
		v = strings.TrimSpace(v)
		if len(v) == 0 {continue}
		if i := strings.Index(v, "="); i < 0 {
			m[v]="true"
		}else {
			m[strings.TrimSpace(v[0:i])]=strings.TrimSpace(v[i+1:])
		}
	}
	return Option(m)
}
func (o Option)Get(k string, val interface{}) bool {
	if v, ok := o[k]; !ok {
		return false
	}else {
		switch val.(type){
			case *int, *uint, *int8, *uint8, *int16, *uint16, *int32, *uint32, *int64, *uint64, *float32, *float64:
			err := json.Unmarshal([]byte(v), val)
			if err != nil {panic(err)}
			case *[]string:
			*val.(*[]string) = o.trim(strings.Split(v, ","))
			case *string:
			*val.(*string) = v
			default:
			panic(fmt.Sprintf("Unsupported type %v", reflect.TypeOf(val)))
		}
	}
	return true
}
func (o Option)Trim() {
	for k, v := range o {
		o[k] = strings.TrimSpace(v)
	}
}
func (o Option)ToString() string {
	buf := bytes.NewBufferString("")
	for k, v := range o {
		buf.WriteString(k)
		buf.WriteString("=")
		buf.WriteString(v)
		buf.WriteString(",")
	}
	return buf.String()
}
func (o Option)trim(s []string) []string {
	for i, v := range s {
		s[i] = strings.TrimSpace(v)
	}
	return s
}