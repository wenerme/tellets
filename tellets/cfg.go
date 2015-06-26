package tellets

import (
	"github.com/go-ini/ini"
	"strconv"
	"flag"
)
type Config struct {
	*ini.File
	Database DatabaseConfig
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

	{
		sec := cfg.Section("database")
		m := sec.KeysHash()
		cfg.Database.Type = m["type"]
		cfg.Database.Host = m["host"]
		cfg.Database.Password = m["password"]
		cfg.Database.User = m["user"]
		cfg.Database.Path = m["path"]
		cfg.Database.Schema = m["schema"]
		cfg.Database.Debug, _ = strconv.ParseBool(m["debug"])
	}

	return cfg
}
