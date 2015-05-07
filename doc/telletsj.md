core
	整理相关信息
web-server
	提供信息服务
	只有读操作
	提供的服务可注册
web
	静态 web 界面,通过 web-server 获取信息做呈现

主要的信息

文章元信息
文章内容
文章列表
作者列表
标签列表
分类列表

主要的功能

文章列表分页
文章搜索条件,准确搜索
	日期
	作者
	标签
	分类


host/perm-link
	应该跳转到 host/perm-link/link 类似于 stackoverflow 的 url 格式

列表页
host/search/category=a,b,c&tag=e,f,g&title=xx&content=xx
host/category/a,b,c
host/tag/a,b,c

json 数据
host/v1/meta/${id}
host/v1/content/${id}
host/v1/search/${conditions}