package tellets

import (
	"github.com/go-ini/ini"
	"strconv"
	"flag"
)
type Config struct {
	*ini.File
}

type DatabaseConfig struct {
	Type     string
	Path     string
	Schema   string
	Host     string
	User     string
	Password string
	Debug    bool
}

var GlobalConfigFile = "tellets.global.ini"
var DefaultConfigFile = "tellets.ini"

func init() {
	var name string
	flag.StringVar(&name, "f", "tellets.ini", "User specified config file.")
	DefaultConfigFile = name
}

func loadConfig() *Config {
	f, err := ini.Load(DefaultConfigFile, GlobalConfigFile)
	if err != nil {
		panic(err)
	}
	f.BlockMode=false
	cfg := &Config{}
	cfg.File = f

	return cfg
}

func (cfg *Config)CollectSources() []string {
	m := cfg.Section("sources").KeysHash()
	sources := make([]string, 0)
	for _, v := range m {
		sources = append(sources, v)
	}
	return sources
}

func (cfg *Config)DatabaseConfig() DatabaseConfig {
	sec := cfg.Section("database")
	m := sec.KeysHash()
	db := DatabaseConfig{}
	db.Type = m["type"]
	db.Host = m["host"]
	db.Password = m["password"]
	db.User = m["user"]
	db.Path = m["path"]
	db.Schema = m["schema"]
	db.Debug, _ = strconv.ParseBool(m["debug"])
	return db
}