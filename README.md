<!-- title: Tellets README -->
<!-- category: Posting -->
<!-- tag: Project -->
<!-- date: 2013/12/7 -->
<!-- state: published -->
<!-- link: tellets -->

* 中文 [说明文档](#tellets-cn)
* English [README](#tellets-en)

<!-- more -->

Tellets-EN
==========

Tellets is a markup based blog platform.

Main features
------------

* Markup based, easy to write.
* Use github to host you posts, easy to manage and edit.
* Template is easy to modify and implement.
* No database.
* No admin board, you don't have to care about you blog site.
* Zero configuration, you can put tellets everywhere with no change.
* Easy to write extension for tellets.
* Integrate disqus and duoshuo  for comments.

Getting Started
---------------

- [Requirement](#requirement)
- [Installation](#installation)
- [Writing Posts](#writing-Posts)
- [Publishing Posts](#publishing-posts)
- [Update Posts](#update-posts)
- [Changing Settings](#changing-settings)
- [Changing Templates](#changing-templates)
- [Enable disqus](#enable-disqus)
- [Updating Tellets](#updating-tellets)
- [MIT-License](#mit-license)

For more, please visit __[WIKI](https://github.com/wenerme/tellets/wiki/)__

DEMO: [MY BLOG](http://blog.wener.me/)

e.g. My blog setting for github repos(where I host my posts):

```php

$plugins['github_repo']['repos'] = preg_split('#[\n\r]+#',<<<REPOS
WenerLove/blog
WenerLove/asp.net.SaySay/README.md
WenerLove/greedy_snake/README.md
WenerLove/java.blog/README.md
WenerLove/tellets/README.md
WenerLove/GTetris/README.md
REPOS
);

```



Requirement
------------

* PHP >= 5.3

* php.ini
	* short-open-tag on

> This directive also affected the shorthand
> <?= before PHP 5.4.0, which is identical to <? echo. Use of this shortcut required short_open_tag to be on. Since PHP 5.4.0, <?= is always available.

* Markdown requirement see [here](https://github.com/michelf/php-markdown#requirement)

> Before PHP 5.3.7, pcre.backtrack_limit defaults to 100 000, which is too small in many situations. You might need to set it to higher values. Later PHP releases defaults to 1 000 000, which is usually fine.

* Nginx Setting: work on it, not test yet

* Apache, Rewrite module

Installation
--------------

Download
[latest zip archive](https://github.com/WenerLove/tellets/archive/master.zip),
unzip to where you want.

Writing Posts
-------------

Example first,
please read a few lines of the [README](https://github.com/WenerLove/tellets/edit/master/README.md)
, here is some `metadata` we need.

```

<!-- title: Tellets README -->
<!-- category: Posting -->
<!-- tag: Project -->
<!-- date: 2013/12/7 -->
<!-- state: published -->
<!-- link: tellets -->

```

`<!-- state: published -->` is required for publish
posts.If `state is not published`, tellets
will ignore this posts.

`<!-- link: tellets -->` is not required,if you
need a perm link, you will need this.
If no link, will generate from title.

Convert code is
[here](https://github.com/WenerLove/tellets/blob/master/app/classes/functions.php#L7)
.

Publishing Posts
-----------------

Require `<!-- state: published -->`.

Update Posts
------------

tellets will not notice the posts change,
you have to do this by visit `action/update`.
A auto update plugin in progress.

Changing Settings
-----------------

Setting file is `data/config.php`.
will generate when first run.
Settings is well comments.

Changing Templates
-------------------

Put you theme in `data/templates`,
change `$template['active']` to the
dir.

Enable disqus
-------------

In `config.php`,  find `$template['comment_type']`
and `$template['comment_user']`.

Set `$template['comment_type'] = 'disqus';` to
enable disqus, or `null` to disable.
Set `$template['comment_user'] = 'wener';` to
you username in disqus.

Updating Tellets
----------------

Currently, the only way to update tellets is
download and unzip again.

MIT-License
------------

tellets is MIT license, different from dropplets.



--------------------

Tellets-CN
========

Tellets 是一个基于Markup的博客平台.

主要特性
-------

* 文章是基于 MarkUp,易于编写
* 可使用github托管文章,管理和编辑方便
* 模板系统简单,易于修改和实现自己的主题
* 无数据库,使用起来轻松方便
* 0 配置
* 简单的Hook系统,编写插件得心应手
* 无繁杂的管理系统,你只需要写就可以了
* 集成了 disqus和多说,可选择打开.

更多信息,请前往 __[wiki](https://github.com/wenerme/tellets/wiki/)__

Getting Started
---------------

- [安装](#安装)
- [撰写文章](#撰写文章)
- [更新文章](#更新文章)
- [改变设置](#改变设置)
- [启用评论](#启用评论)
- [更换模板](#更换模板)
- [更新Tellets](#更新Tellets)
- [MIT 许可](#许可)

DEMO: [我的博客](http://blog.wener.me/)

我博客使用的repos(即所有的文章来源,均为托管在github的项目)

```
$plugins['github_repo']['repos'] = preg_split('#[\n\r]+#',<<<REPOS
WenerLove/blog
WenerLove/asp.net.SaySay/README.md
WenerLove/greedy_snake/README.md
WenerLove/java.blog/README.md
WenerLove/tellets/README.md
WenerLove/GTetris/README.md
REPOS
);
```


Tellets 存在的主要目的
-----------------

使写博文和博客完全分离.使用 tellets 你可以使用任意你喜欢的工具
编写,可以在任意地方写,可以随时离线或在线书写,只是简单的写博文,
简简单单的,没有任何限制.比如我可以使用 stack.io 写文章,
然后同步到 github. 需要关注的只有写这一部分,其他的,与写文章无关的
都不需要考虑, tellets 会给你做好呈现这一部分.

使用传统的书写方式过于繁杂,而是用标记语言则舒畅多了.
或许这对于不了解 标记语言 的人来说,需要一点学习时间,
但是我想学习一门标记语言也是很快而且很值得的.当然,
你也可以完全使用 HTML,不需要更改,因为 markdown 是兼容
HTML的.

Tellets 足够简单轻便,随便你放在哪里,他都能很好的正常运行.
虽然对于 Nginx 需要添加一些,但是一切都比你想象的简单,
没有繁杂的设置,数据库等等.你需要专注的就是写好文章即可.

默认安装的插件
-------------

* github_repo 将文章托管到github
* remote_admin 通过一个网络文件对博客进行配置管理(尚未完成)

已定义的元信息
--------------

* title
* date
* category
* tag Array
* link string 作为生成链接的依据,默认的链接生成是从 title生成的
* ext string 该文章的扩展名
* hash string 文章内容的 sha1,作为文章的唯一id

Tellets 的由来
--------------

最开始我是从[dropplets](https://github.com/circa75/dropplets)
 [fork](https://github.com/wenerme/dropplets)的,
很喜欢 dropplets 的简单,但是很多达不到我想要的,原项目也不便于修改,
所以我就将根据从 dropplets 受到的启发,重写成了 tellets.

Tellets 这个名字中的 lets 是继承自 dropplets.
而取名 tellets 是意为 `let's tell`.

搭建需求
-------

PHP
: >= 5.3

short-open-tag
: 在模板中为了简洁, 使用了很多 PHP 代码开始标志的缩写方式,
在 PHP < 5.4 的时候 最好设置 [short-open-tag](http://www.php.net/manual/zh/ini.core.php#ini.short-open-tag),
我有尝试在运行时使用 ini_set 设置,但是似乎没有成功.

Nginx 设置
: Working on it

Apache
: 需要有 Rewrite 模块, 这个基本都有的.

安装
-----

下载最新的 zip 包,解压到你想安装的地方.访问解压的位置,设置密码.ok,一切大功告成.

撰写文章
-------

在撰写文章之前,请看一下这个 [README 的前 几行](https://github.com/WenerLove/tellets/edit/master/README.md).
这个 README 其实就是一篇博文,你会注意到文章开头的 HTML 注释,
`<!-- title: Tellets README -->`.我管这个叫做 `元信息`,主要用于告诉 tellets 这是什么.

发布一篇博文,必要的字段是 `<!-- state: published -->`,
只有状态是 `published` 的博文才会在 tellets 上显示.

同时一篇博文你或许还希望有 title, date,分别表示文章标题和发布时间.
接下来的内容就自己发挥了.

对了, 你可能还会注意到 `<!-- more -->`, 如果你使用过 WP,那你并不会陌生.
在 more 之上的内容为该博文的简介,在文章列表中只会显示该简介.

关于所有的元信息,你可以查看[已定义的元信息](#已定义的元信息)

更新文章
-------

Tellets 不会自动去检测文章是否有变化,因为这样过于繁琐,
更新需要手动进行操作,不过也比你想象的简单,直接访问
`HOST/action/update` 即可完成更新.

改变设置
-------

Tellets 的设置会保存在 `data/config.php` 中,
该文件由第一次运行时自动生成, 生成后的配置文件中有对配置项的注释
,你可以很容易的理解并且修改.

启用评论
-------

在配置文件中,你会注意到以下两项配置,设置好这两个值即可.

```
/*
启用的社交评论插件,如果为false等值(例如:空字符串,null或false) 则不启用
可能的值为 disqus 或 duoshuo 等,在默认模板里面支持这两个
设置该值后需要设置 comment_user
注意: 评论插件的显示需要模板支持.
*/
$template['comment_type'] = 'disqus';


/*
当设置 with_comments 启用评论插件后
使用该值来设置绑定的用户
*/
$template['comment_user'] = 'wener';
```

更换模板
-------

将模板解压到 `data/templates` 中,在设置中 更改 `$template['active'] = /*模板目录*/`.

更新Tellets
------------

现在唯一的方法就是覆盖更新了.

许可
----

因为 Tellets 是我完全重写的,直接选择的 MIT 许可, 与 原生的 dropplets 不同.


