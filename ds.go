package tellets

type DataSource interface {
	CheckUpdate()
	Uri() string
}