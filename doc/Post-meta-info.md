Post meta info
=============

Standard meta
--------------

| Meta Name | Type      | Description  |
| --------- |:---------:|:------------:|
| title     | string    | Title of post
| category  | string    |  Category of post
| tag       | array     | tags of post
| date      | date format | Post date, possible format see [here](http://php.net/strtotime)
| link      | string    | act like a perm link, if no link, will use title to generate link
| state     | string    | post state, tellets only care about `published`


Generate meta
-------------

| Meta Name | Type      | Description  |
| --------- |:---------:|:------------:|
| hash      | string    | hash of the origin content
| ext       | string    | extension of origin file
| track 	| string    | where this post come from.Generate by some plugin.
