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


文章元信息内容

title
: string
: 文章标题,会呈现在 url 中

category
: array
: 文章分类

tag
: array
: 文章标签

date
: date
: 发表时间

modify-date
: date
: 上次修改时间

link
: string
: 文章URL中显示, 如果不指定, 则会使用标题来生成

perm-link
: string
: 永久链接, 如果没有, 则使用 link
: 可以为一串数字或简单的字符,要求唯一

state
: string
: 发布状态, 只有发布状态为 `published` 才会被现实

features
: array
: 指定一些特性,主要用于提示解析器或前端等做一些特性上的处理

format
: string
: 文章内容格式类型,例如 markdown,gh-markdown,slides
: 前端会根据不同的类型来呈现内容


tellets 基于事件驱动
