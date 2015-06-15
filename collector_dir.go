package tellets
import (
	"net/url"
	"gopkg.in/fsnotify.v1"
)
var fsWatcher *fsnotify.Watcher

type directoryCollector struct {
}

func (c *directoryCollector)Collect(u *url.URL) ([]Collection, error) {
	if u.Scheme != "file" {
		return nil, ErrCanNotCollect
	}
	fsWatcher.Add(u.Path)
	return nil, nil
}

func (c *directoryCollector)Init() interface{} {
	return func(cs CollectServer) {
		go func() {
			for {
				e := <-fsWatcher.Events
				switch e.Op{
				case fsnotify.Remove:
				default:
					url, err := url.Parse("file://./"+e.Name)
					if err != nil { log.Warning("Parse url faield", err); continue }
					cs.Discover(url)
					log.Debug("fsnotify %s", url.String())
				}
			}
		}()
	}
}

func init() {
	var err error
	fsWatcher, err = fsnotify.NewWatcher()
	if err != nil {panic(err)}
	RegisterCollector(func() Collector {
		return &directoryCollector{}
	})
}