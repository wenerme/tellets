package tellets
import (
	"strings"
	"bytes"
)
type Option map[string]string

func CreateOption(s string) Option {
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