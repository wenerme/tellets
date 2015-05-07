package main
import (
	"regexp"
	"io/ioutil"
	"fmt"
	"strings"
	tellets "../."
)


func main() {
	t4()
}

func t4() {
	b, err := ioutil.ReadFile("README.md")
	if err != nil { panic(err)}
	c := string(b)
	m, err := tellets.HtmlCommentParser.Parse(c)
	if err!= nil {panic(err)}
	fmt.Printf("%+v\n", m);
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