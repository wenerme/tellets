<!-- title: Tellets - A lightweight blogging platform -->
<!-- category: Posting -->
<!-- tag: Project,Go,Java,PHP -->
<!-- date: 2013/12/7 -->
<!-- last-modified-date: 2015/5/7 -->
<!-- state: published -->
<!-- link: tellets-a-lightweight-blogging-platform -->
<!-- perm-link: tellets -->
<!-- format: markdown -->
<!-- option: comment=true -->
<!-- author: wener<wener@wener.me>(http://hi.wener.me) -->

__OUTDATED__ Readme is outdated

Tellets
==========
Tellets - Let's tell! -  A lightweight blogging platform.


Main features
------------

* Markup based, easy to write.
* Use Github to host you posts, easy to manage and edit.
* Template is easy to modify and implement.
* No database.
* No admin board, you don't have to care about you blog site.
* Zero configuration, you can put tellets everywhere with no change.
* Easy to write extension for tellets.
* Integrate disqus and duoshuo  for comments.


<!-- more -->


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
