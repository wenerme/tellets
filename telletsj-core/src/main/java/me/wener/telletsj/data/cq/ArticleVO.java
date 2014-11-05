package me.wener.telletsj.data.cq;

import java.util.List;
import java.util.Map;
import me.wener.telletsj.data.Article;
import me.wener.telletsj.data.ArticleInfo;
import me.wener.telletsj.data.ArticleState;
import me.wener.telletsj.data.Category;

public abstract class ArticleVO implements Article, ArticleInfo
{
    private String title;
    private String link;
    private String sha;
    private String content;
    private String description;
    private long timestamp;
    private ArticleState state;

    public List<String> tags;
    public List<Category> categories;
    public Map<String,String> meta;
}
