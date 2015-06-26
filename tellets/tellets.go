package tellets
import (
	"github.com/jinzhu/gorm"
	_ "github.com/mattn/go-sqlite3"
	"reflect"
)


type Tellets interface {
	Config() *Config
	Hook() Hook
	Context() Context
}

type tellets struct {
	db  gorm.DB
	c   Context
	h   Hook
	cfg *Config
}
func (t *tellets)Config() *Config {
	return t.cfg
}
func (t *tellets)Hook() Hook {
	return t.h
}
func (t *tellets)Context() Context {
	return t.c
}
func NewTellets() Tellets {
	return newTelletsByOption(loadConfig())
}
func newTelletsByOption(c *Config) Tellets {
	ctx := NewContext("Tellets")
	t := &tellets{
		c:ctx,
		h:NewHook("Tellets"),
		cfg:c,
	}
	{
		cfg := t.Config().DatabaseConfig()
		log.Info("Create Storage with %#v", cfg)

		db, err := gorm.Open(cfg.Type, cfg.Path)
		if err != nil {panic(err)}
		db.LogMode(cfg.Debug)
		db.AutoMigrate(&Post{}, &Category{}, &Tag{}, &Author{})
		t.db = db
	}

	ctx.AddType(t, reflect.TypeOf((*Tellets)(nil)).Elem())
	ctx.Add(t.cfg)
	ctx.Add(t.db)

	// Init Plugins
	for _, f := range plugins {
		f(t)
	}
	t.Hook().DoHook(HookInitTellets, t.Context())
	return t
}