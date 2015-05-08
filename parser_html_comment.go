package tellets
import (
	"regexp"
	"strings"
	"fmt"
	"time"
	"github.com/golang/glog"
	"errors"
)

// 以 HTML 注释的形式
// <!-- title: Tellets README -->
// <!-- category: Posting -->
// <!-- tag: Project -->
// <!-- date: 2013/12/7 -->
// <!-- state: published -->
// <!-- link: tellets -->
type htmlCommentParser struct { }
var (
	regHtmlCommentSection = regexp.MustCompile(`(?m)^\s*<!---*\s*(?P<type>more|summary|paging)\s*-*?-->\s*$`)
	regHtmlCommentSinglePair = regexp.MustCompile(`(?m)^\s*<!---*(?P<key>[^:]+):(?P<value>.*?)-*?-->\s*$`)
	regHtmlCommentAllPair = regexp.MustCompile(`\A(\s*<!---*(?P<key>[^:]+):(?P<value>.*?)-*?-->)+`)
	regHtmlCommentFilenameTest = regexp.MustCompile(`\.(md|markdown|html|htm)$`)
)
func (htmlCommentParser)Name() string {
	return "HtmlComment"
}
func (htmlCommentParser)CanParse(fn string) bool {
	return regHtmlCommentFilenameTest.Match([]byte(fn))
}
func (htmlCommentParser)Parse(c string) (*Meta, error) {
	metaStr := regHtmlCommentAllPair.FindString(c)
	m := matchToMap(regHtmlCommentSinglePair, metaStr)
	meta := &Meta{}
	mapToMeta(m, meta)
	parts := regHtmlCommentSection.Split(c, -1)
	if len(parts)> 1 {
		sum := parts[0]
		sum = sum[len(metaStr):]
		//		sum = strings.TrimSpace(sum)
		meta.Summary = sum
	}
	return meta, nil
}
func matchToMap(r *regexp.Regexp, c string) (map[string]string) {
	m := make(map[string]string)
	match := r.FindAllStringSubmatch(c, -1)
	for _, v := range match {
		result := make(map[string]string)
		for i, name := range r.SubexpNames() {
			result[name] = v[i]
		}
		if k, ok := result["key"]; ok {
			m[strings.TrimSpace(k)] = strings.TrimSpace(result["value"])
		}
	}
	return m
}
func mapToMeta(m map[string]string, meta *Meta) (err error) {
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

	meta.Title = m["titile"]
	meta.PermLink = m["perm-link"]
	meta.Link = m["link"]
	meta.Format = m["format"]
	meta.Categories = strings.Split(m["category"], ",")
	meta.Tags = strings.Split(m["tag"], ",")
	meta.Author = strings.Split(m["author"], ",")
	meta.Features = strings.Split(m["feature"], ",")
	meta.State = m["state"]
	{
		d := m["date"]
		if d != "" {
			meta.Date = parseDate(d)
		}
	}
	{
		d := m["last-modified-date"]
		if d != "" {
			meta.LastModifiedDate = parseDate(d)
		}
	}
	return
}

type metaMap map[string]string
func (m metaMap)Get(k string) string {
	return m[k]
}
func (m metaMap)TryGet(k string, def string) string {
	if v, ok := m[k]; ok {
		return strings.TrimSpace(v)
	}
	return def
}
func (m metaMap)TryGetAndTrim(k string, def string) (string) {
	return strings.TrimSpace(m.TryGet(k, def))
}
func (m metaMap)GetAndTrim(k string) (string) {
	return strings.TrimSpace(m.Get(k))
}

func oneOfKey(m map[string]string, keys... string) string {
	for _, k := range keys {
		if v, ok := m[k]; ok {
			return strings.TrimSpace(v)
		}
	}
	panic(fmt.Sprint("Require one of keys ", keys))
}

var timeFormats[]string = []string{
	time.ANSIC,
	time.RFC3339,
	time.RFC1123,
	"2006/1/2 15:4",
	"2006/01/02 15:4",
	"2006-1-2 15:4",
	"2006-01-02 15:4",
	"2006/1/2",
	"2006/01/02",
	"2006-1-2",
	"2006-01-02",
}
func parseDate(s string) *time.Time {
	for _, f := range timeFormats {
		t, err := time.Parse(f, s)
		if err == nil { return &t}
	}

	glog.Error("Can not parse date "+s)
	return nil
}

func init() {
	MetaParserManager.Register(&htmlCommentParser{})
}