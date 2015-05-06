# GO
* [ ] 实现 Github 数据读取
* [ ] 解析元信息
* [] 存储元信息和文章数据
* [] REST 服务提供元信息和文章数据


# PHP

### X 从写的项目名 更改为 tellets 继承 dropplets 的 后缀,tell means you wanner say somthing.

tellets also mean  lets tell

### X 修改 配置设置到 dropplets/config.php
### X 将 Post 作为一个类
### X dropplets 类，包括大部分系统操作。

### X 修改 dropplets 的内容元字段

···
<!-- title: this is the post title -->
<!-- author: wener -->

<!-- category: this is the post title -->
<!-- tag: story -->

<!-- date: posting -->

<!-- key: value -->
···

作为一种可选方式，该元字段只能出现在文章的开头。

在缓存文章的时候需要把文章的元字段给存储起来，这样方便与检索和分类信息的显示。

元字段作为 key/value 的数据信息，考虑在使用其它扩展的时候也会使用到该特性，所以相对来说比较有必要性。

元字段的名值会经过如下处理

1. trim key 和 value
2. 将key中多个空格替换为单个空格

### X 修改以支持 <!-- more -->

···
description here.

<!-- more -->

the content.
···

处理的时候需要把描述之前的提取出来，也需要使用 markdown 格式化，然后作为元字段

### 使用插件实现 与 github 整合

将 github 作为文件源

### 修改以支持 i18n 的文章

以文件名作为标示

比如可以有以下文章

* this-is-first-post-zh.md 
* this-is-first-post-en.md 

等，选择如果不存在 ·this-is-first-post.md ·，则先选择默认语言的文章，默认语言在配置里有。否则选择第一个获取到的文章。

### X 内容模板修改为只包含主要的 body

	这样只需要替换body就好了。而不是缓存整个页面，这个到时候再考虑。

### X 发布文章

只有 state 为 publish 的时候才会显示文章. 如果 没有 state字段,则默认为 draft
	

### X url 跳转

```
/category/{category}
/tag/{tag}
/action/(update|invalidate|login|logout|forgot)
/admin/
/{post title}
```

Dropplets 运行流程
------------------

* 先检测是否存在 config.php, 作为是否是第一次运行的判断
* 获取路径中的文件名和分类参数
	* 如果不存在 filename == null,则为主页
	* 如果 filename 为 rss 或 atom,则生成相应输出
	* 否则则以单页来处理,eg. youre-ready-to-go
	

项目文件结构
-----------

* /data
	* /cache 存放缓存数据
	* /posts 将文章放在这里
	* /locales
	* config.php 配置文件,在第一次运行后生成,会在配置更改后改变
* /app
	* boostrap.php
	* /lib 第三方库依赖
	* /classes
* index.php

markdown file extension
--------------------

```
.markdown
.md
.mdown
.mkd
.mkdn
.mdwn
.mdtxt
.mdtext
.text
```

#\.(markdown|md|mdown|mkd|mkdn|mdwn|mdtxt|mdtext|text)$#i


参考 http://superuser.com/questions/249436/file-extension-for-markdown-files

其他扩展名参考 https://github.com/github/markup



参考
------

* 国家号 https://github.com/umpirsky/country-list
* 在线 HTML2Markdown 工具 http://html2markdown.com/

dropplets | tellets
-|-
将标题,作者,分类等信息固定在文档内部 | 将这些信息放在 注释中,不影响内容
使用 Markdown | 使用 [Markdown-Extra](https://github.com/michelf/php-markdown/)

我的注记
-------

获取文件列表

https://api.github.com/repos/WenerLove/note/contents/

内容

https://api.github.com/repos/WenerLove/tellets/contents/TODO.md?ref=master

获取master的treeurl

https://api.github.com/repos/WenerLove/tellets/branches/master

如果有 message，则直接显示，不在进行下面的，一般会出现note found

递归获取所有文件

https://api.github.com//repos/WenerLove/tellets/git/trees/0079abacd75914308717b68b104e149e900c767c?recursive=1

尚且没有完成的
---------------

* X 根据 state 不同来判断是否显示，这里考虑实在addpost的时候抛弃，或者是在parser里抛弃

如果在parser理抛弃，则会少处理很多步骤，那么parser返回什么呢？ 一个空的Post对象，state不为published
addPost的时候继续抛弃

考虑是否让parser完成所有步骤，这样有利于以后更改，因为解析的次数很少，所以也没啥影响。

* 尚未实现 管理
http://econsultancy.com/cn/blog/62844-24-beautifully-designed-web-dashboards-that-data-geeks-will-love
http://stackoverflow.com/questions/13839920/open-source-library-framework-for-building-dashboard-bar-line-pie-etc

关于是否实现管理,很多操作都不一定需要管理的, tellets 的定位是 基于markup,
使用github作为源的博客系统,不需要经常上网站进行管理,管理方法就类似于写文章一样,
但是文章操作过一次后,就不方便改变,也就是一次操作,包含了一次更新,之后便不能再进行
此操作.

remote admin

hook: action/update,更新后保存日志到服务器,然后直接跳转到主页
remote setting: xxx.ini,使用file_get_contents来获取,
所以把链接作为github的raw链接即可.

主要进行的管理操作: 更新,更换模板(以后考虑)

ini.

```
[config]
last_update_time=xxxxx ; 最后一次跟新时间,获取的时候会和
```


* X 尚未实现 rss atom
* 实现一个简单的 plugin，posts_dir_recursive_import 递归的导入posts目录下的所有文章，默认是只导入 posts目录的
* X 尚未实现 action
* X 尚未实现翻页
* X 尚未实现 link 元字段指定持久链接
* 尚未实现多作者
* X 尚未实现文章导航 上一篇文章和下一篇文章
* X 添加 Tag 和 Category 列表到 footer
* X 实现 github_repo
* X 修改 config,以支持数组类型的配置选项
* X 实现 tags,可以搜索多个标签
* X Config 增加 ns, PLUGINS,TEMPLATES
更改后的访问方式:
```
$config['name'] 将访问到 CONFIG
$config['templates']/$config['plugins']将访问 TEMPLATES 和 PLUGINS
```
* X config.php 保存时,增加 $plugins 和 $templates
* X 集成 Disqus 或 多说
* X 移除 markdown.php 使用Michelf\Markdown
* -------------- v 1.1
* X 完善 feed
* X 完善 markdown 的样式显示
* X 添加 基本的meta,例如 generator
* X 添加 with_header 和 with_footer 选项,用于在生成时加入到页面中
* X 添加 ext 元字段到Post,用于记录原来的内容格式.
* --------------- v1.2
* 实现 log
* X 添加 HTMLParser
* 实现 message 页面消息的传递
* X 添加一个 github helper,以实现更好的缓存,
记录repo的sha,缓存tree,减少网络访问,增加解析速度
GitHubHelper 因为不是 tellets必须的,所以以插件的形式添加.
GitHubHelper 是简单的GitHub操作的包装.
* X 给 github_repo 添加一个 auth 选项,作为默认的auth, 该选项已由 github_helper 实现
* X github_repo 使用 GitHubHelper 来进行操作
* X 添加 debug 选项,配置是否显示错误等.
* ---------------v 1.3


BUGS
----

* X 在 默认的 autoload无效时,添加的一个autoload 不能实现有ns的加载
* 修改第一次输入密码时的图标问题
* 第一次初始后,需要UPDATE
* 安装时的环境检测, 安装时遇到 mb_internal_encoding undefined 错误.


其他工作
-------

* 添加 nginx 测试
* 再添加一个模板,使用 bootstrap.
* 在京东云 app 上搭建 tellets  
这个暂时不能实现,因为app不支持文件/目录的读写
* 添加一个 MongoDB PostHelper,
这样使用mongodb来保存文章就可以了
,但是还有个问题是,config的保存,虽然在上传时可以写一部分配置,但是不能下载和编辑
,因此只能/必须使用remote_admin插件的功能,也必须要该插件能工作时
tellets 才能部署在 jae 上.
还有,git的缓存,必须要使用数据库实现缓存~?这个也是个问题!

其他特性目标
--------

* auto_update 实现后台 update, 时间间隔的update, 使用文件锁实现只允许一个 update
* tellets 的 favicon
* 多语言文章版本
* 界面i18n支持
* 添加 template 元信息支持,可针对文章选择模板


上下文
----------

全局 $tellets, $postHelper, $config, $request

模板
$configt 为配置中 Template 配置的缩写
$config 为配置中 Plugins 配置的缩写
$post, $posts

需求
----
password_compat
: PHP >= 5.3

REGEX
-----

###repostring match

use

```
(?<user>[^/]+) # user name require
(?:/(?<repo>[^/:]+)) # repo require
(?::(?<branch>[^/]+))? # branch optional
(?:/(?<path>.*))? # path optional
```

match
```
username/repo:master/path/to/content;auto=user|password
username/repo/path/to/content;auto=user|password
username/repo:master;auto=user|password
username;auto=user|password
```
