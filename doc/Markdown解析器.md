Markdown 解析器
===============

扩展名匹配
---------

.(markdown|md|mdown|mkd|mkdn|mdwn|mdtxt|mdtext|text)

参考 [file-extension-for-markdown-files](http://superuser.com/questions/249436/) on stackoverflow.

Meta 格式
---------

* 使用 HTML 注释格式指定元信息 `<!-- key:value -->`
* 多值分割符 `,|`
* more 匹配格式 `^\s*<!--\s*more\s*-->\s*$`

实现
----

pegdown



