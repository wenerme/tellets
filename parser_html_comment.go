package tellets
import (
	"regexp"
	"strings"
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
type htmlCommentParser struct {}
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
func (p htmlCommentParser)Parse(c *Collection) (*Meta, error) {
	if !p.CanParse(c.Filename) {
		return nil, ErrCanNotParse
	}
	metaStr := regHtmlCommentAllPair.FindString(c.Content)
	m := matchToMap(regHtmlCommentSinglePair, metaStr)
	meta := &Meta{}
	mapToMeta(m, meta)
	parts := regHtmlCommentSection.Split(c, -1)
	if len(parts)> 1 {
		sum := parts[0]
		sum = sum[len(metaStr):]
		//		sum = strings.TrimSpace(sum)
		meta.SetIntro(sum)
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

	meta.SetTitle(m["titile"])
	meta["perm-link"] = m["perm-link"]
	meta["link"] = m["link"]
	meta["format"] = m["format"]
	meta.Categories = strings.Split(m["category"], ",")
	meta.Tags = strings.Split(m["tag"], ",")
	meta.SetAuthors = strings.Split(m["author"], ",")
	meta.SetFeatures(strings.Split(m["feature"], ","))
	{
		d := m["date"]
		if d != "" {
			meta.SetDate(parseDate(d))
		}
	}
	{
		d := m["last-modified-date"]
		if d != "" {
			meta.SetLastModifiedDate(parseDate(d))
		}
	}
	return
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
	//	T.Hook(HookInit, func() {
	//		MetaParserManager.Register(&htmlCommentParser{})
	//	})
}