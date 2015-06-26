package main
import (
	"regexp"
	"io/ioutil"
	"fmt"
	"strings"
	"github.com/wenerme/tellets/tellets"
//	"gopkg.in/yaml.v2"
	"github.com/go-ini/ini"
	"flag"
	"net/url"
	"os"
	"path"
	"path/filepath"
)

var GlobalConfigFile = "tellets.global.ini"
var DefaultConfigFile = "tellets.ini"

func main() {
	_ = tellets.Version

	t8()
}

type Schema interface {
	Name() string
	List(string) ([]string, error)
	Content(string) (string, error)
}

func fileSchemaHandler(s string) ([]tellets.Entry, error) {
	u, err := url.Parse(s)
	if err!= nil {return nil, err}
	p := u.Path
	if p == "" { p = u.Opaque }

	items := make([]tellets.Entry, 0)
	if strings.Index(p, "*") >= 0 || strings.Index(p, "?") >= 0 {
		p, err = filepath.Abs(p)
		if err!= nil {return nil, err}
		files, _ := filepath.Glob(p)
		for _, f := range files {
			e := tellets.NewEntry()
			e.Filename= path.Base(f)
			e.Source = s
			e.EntrySource = "file:"+f
			items = append(items, e)
		}
	}else {
		fi, err := os.Stat(p)
		if err!= nil {return nil, err}
		if fi.IsDir() {
			files, _ := ioutil.ReadDir(p)
			for _, f := range files {
				abs, _ := filepath.Abs(path.Join(p, f.Name()))
				e := tellets.NewEntry()
				e.Filename=f.Name()
				e.Source = s
				e.EntrySource = "file:"+abs
				items = append(items, e)
			}
		}else {
			e := tellets.NewEntry()
			abs, _ := filepath.Abs(p)
			e.Filename = path.Base(p)
			e.EntrySource = "file:"+abs
			e.Source = s
			items = append(items, e)
		}
	}

	return items, nil
}

func t10() {
	ok, err := filepath.Match("*.md", "abc/a/b/.md")
	fmt.Println(ok, err)
	ok, err = filepath.Match("**.md", "abc/a/b/.md")
	fmt.Println(ok, err)
	ok, err = filepath.Match("abc/a/*/*", "abc/a/b/x.md")
	fmt.Println(ok, err)
	//	var a interface{}
	//	fmt.Println(a.(*github.Repository))
}

func t8() {
	pwd, err := os.Getwd()
	fmt.Println(pwd)
	if err != nil { panic(err) }
	s := "file:./posts/"
	s = strings.Replace(s, "$PWD", pwd, -1)
	fmt.Println(s)
	u, err := url.Parse(s)
	if err != nil { panic(err) }
	fmt.Println(u.Path)
	fmt.Println(u)
	fmt.Printf("%#v\n", u)

	entries, err := fileSchemaHandler("file:./posts/../*/a*.md")
	if err != nil { panic(err) }
	for _, e := range entries {
		fmt.Printf("%#v\n", e)
	}

}

type Info struct {
	PackageName string
}

func t7() {
	i := &Info{}
	err := ini.MapToWithMapper(&i, ini.TitleUnderscore, []byte("packag_name=ini"))
	if err != nil { panic(err) }
	fmt.Printf("%#v\n", i)

}
func t6() {
	f, err := ini.Load(DefaultConfigFile, GlobalConfigFile)
	if err != nil { panic(err) }
	f.BlockMode=false

	flag.String("name", "foo", "a string")

	fmt.Println(f.Section("github").KeysHash()["token"])
	fmt.Println(f.Section("security").KeysHash()["cors"])
	fmt.Println(f.Section("database").KeysHash())

}


func t3() {
	m := make(map[string]string)
	m["a"]="b"
	fmt.Println("OUT", m["X"])
}

func matchToMap(r *regexp.Regexp, c string) (map[string]string, error) {
	m := make(map[string]string)
	match := r.FindAllStringSubmatch(c, -1)
	fmt.Println(match)
	for _, v := range match {
		result := make(map[string]string)
		for i, name := range r.SubexpNames() {
			result[name] = v[i]
		}
		if k, ok := result["key"]; ok {
			m[strings.TrimSpace(k)] = strings.TrimSpace(result["value"])
		}
	}
	return m, nil
}
func t2() {
	b, err := ioutil.ReadFile("README.md")
	if err != nil { panic(err)}
	c := string(b)
	//	fmt.Println(c)
	reg, err := regexp.Compile(`(?m)^\s*<!---*(?P<key>[^:]+):(?P<value>.*?)-*?-->\s*$`)
	regAll, err := regexp.Compile(`\A(\s*<!---*(?P<key>[^:]+):(?P<value>.*?)-*?-->)+`)
	if err != nil { panic(err)}

	m := reg.FindAllStringSubmatch(regAll.FindString(c), -1)

	for _, v := range m {
		fmt.Println(v[1], ":", v[2])
	}
	fmt.Println(regAll.FindString(c))
	fmt.Println(matchToMap(reg, regAll.FindString(c)))
}
func t1() {
	b, err := ioutil.ReadFile("README.md")
	if err != nil { panic(err)}
	c := string(b)
	reg, err := regexp.Compile(`(?m)^\s*<!---*\s*(?P<type>more|summary|paging)\s*-*?-->\s*$`)
	if err != nil { panic(err)}

	m := reg.FindAllStringSubmatch(c, -1)

	for _, v := range m {

		fmt.Println(v[1])
	}
}