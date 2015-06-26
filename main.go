package main
import (
	"github.com/wenerme/tellets/tellets"
	"fmt"
	"flag"
)

func main() {
	flag.Parse()
	t := tellets.NewTellets()
	cs := tellets.NewCollectServer(t)
	_ = cs
	fmt.Printf("Tellets %s lanuched", tellets.Version)

}