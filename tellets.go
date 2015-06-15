package tellets
import (
	"github.com/op/go-logging"
	"os"
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
