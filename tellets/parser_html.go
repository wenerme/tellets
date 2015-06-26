package tellets
import (
	"regexp"
	"strings"
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
func (p htmlCommentParser)Parse(c *Entry) error {
	if !p.CanParse(c.Filename) {
		return ErrCanNotParse
	}
	metaStr := regHtmlCommentAllPair.FindString(c.Content)
	m := matchToMap(regHtmlCommentSinglePair, metaStr)
	post := c.Post
	mapToMeta(m, c)
	parts := regHtmlCommentSection.Split(c.Content, -1)
	if len(parts)> 1 {
		sum := parts[0]
		sum = sum[len(metaStr):]
		//		sum = strings.TrimSpace(sum)
		post.Intro = sum
	}
	post.Content = c.Content[len(metaStr):]
	return nil
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
func mapToMeta(m map[string]string, entry *Entry) (err error) {
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

	post := entry.Post
	o := Option(m)
	o.Get(&post.Title, "title")
	o.Get(&post.PermLink, "perm-link")
	o.Get(&post.Link, "link")
	o.Get(&post.Format, "format")
	o.Get(&post.Categories, "category")
	o.Get(&post.Tags, "tag")
	o.Get(&post.Authors, "author")
	o.Get(post.PublishDate, "date")
	o.Get(post.ModifiedDate, "last-modified-date", "modified-date")
	return
}



func init() {
	RegisterParser(func(t Tellets) Parser {
		return htmlCommentParser{}
	})
}