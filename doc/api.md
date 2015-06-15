
Overview
========
Current api path `/api/v1`

Error Response
---------------
```
{
	"message": "Error message"
}
```

Tellets
=======
Basic tellets information

Get statistics
---------------
```
/stat
```

### Response
```
{
	"categories":{
		"Tech": 10
	},
	"tags":{
		"Go": 5,
		"Java": 5
	},
	"total": 13,
	"authors":[
		{
			"name":"wener",
			"email":"wener(AT)wener.me",
			"website":"http://hi.wener.me"
		},
		{
			"name":"xxx",
			"email":"xxx(AT)wener.me"
		}
	]
}
```

Meta
====
元数据可通过多种方式获取,包括搜索等

Get meta by hash
---------------
```
/meta/hash/:hash
```

### Response
```
{
	
}
```

Find meta by link, perm-link or hash
---------------
```
/meta/find/:string
```

* string can be hash, link, perm-link of hash


List meta by search
---------------
```
/meta/search?tags=a,b&categories=f,g&title=in-title&summary=in-summary&sortby=title&order=desc&page=2&number=10
```

* Common list/page parameter
	* sortby: title, date
	* orderby: asc, desc
	* number: Number pre page
	* page: page number


### Response
```
{
	"list":[ Meta ],
	"page": 1,
	"number": 10,
	"page_count": 5 
	"count": 50,
	"prev": '/meta/search?tags=a,b&categories=f,g&title=in-title&summary=in-summary&sortby=title&order=desc&page=1&number=10',
	"next": '/meta/search?tags=a,b&categories=f,g&title=in-title&summary=in-summary&sortby=title&order=desc&page=3&number=10'
}
```

List meta by recent publish
----------------
```
/meta/list/by/publish-date
```

### Response
List of Meta

List meta by last modified date
----------------
```
/meta/list/by/last-modified-date
```

### Response
List of Meta

Content
=======
只有一种方式可获取到内容

Get content by hash
-------------------
```
/content/:hash
```