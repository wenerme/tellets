package tellets
import (
	"github.com/go-martini/martini"
	"github.com/martini-contrib/cors"
	"github.com/martini-contrib/gzip"
	"net/http"
	"encoding/json"
)
var Server = ""

func init() {
	log.Info("Lanching api server")
	m := martini.Classic()
	m.Get("/", func() string {
		return "Hello world!"
	})

	m.Group("/api/v1", func(r martini.Router) {
		r.Get("/meta/:hash", func(p martini.Params) string {
			return "You need hash "+p["hash"]
		})
		r.Get("/meta", func(req *http.Request) string {
			return "Query by condition SHA:" + req.FormValue("sha")
		})
		r.Get("/owner", func() string {
			j, err := json.Marshal(T.Author())
			if err != nil {panic(err)}
			return string(j)
		})
	})

	static := martini.Static("data/assets", martini.StaticOptions{Fallback: "/index.html", Exclude: "/api/v"})
	m.NotFound(static, http.NotFound)

	// CORS for https://foo.* origins, allowing:
	// - PUT and PATCH methods
	// - Origin header
	// - Credentials share
	m.Use(cors.Allow(&cors.Options{
		AllowOrigins:     []string{"*"},
		AllowMethods:     []string{"GET"},
		AllowHeaders:     []string{"Origin"},
		ExposeHeaders:    []string{"Content-Length"},
		AllowCredentials: true,
	}))
	// gzip every request
	m.Use(gzip.All())

	go m.Run()
}