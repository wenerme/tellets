package me.wener.telletsj.data.cq;


import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.attribute.SimpleNullableAttribute;
import com.googlecode.cqengine.query.option.AttributeOrder;
import me.wener.telletsj.data.Article;
import me.wener.telletsj.data.ArticleState;

class CQArticle
{
    public static final Attribute<Article, String> TITLE = new SimpleAttribute<Article, String>("TITLE")
    {
        public String getValue(Article Article) { return Article.getTitle(); }
    };

    public static final Attribute<Article, String> LINK = new SimpleNullableAttribute<Article, String>("LINK")
    {
        public String getValue(Article Article) { return Article.getLink(); }
    };

    public static final Attribute<Article, String> SHA = new SimpleAttribute<Article, String>("SHA")
    {
        public String getValue(Article Article) { return Article.getSha(); }
    };

    public static final Attribute<Article, String> CONTENT = new SimpleAttribute<Article, String>("CONTENT")
    {
        public String getValue(Article Article) { return Article.getContent(); }
    };


    public static final Attribute<Article, String> DESCRIPTION = new SimpleNullableAttribute<Article, String>("DESCRIPTION")
    {
        public String getValue(Article Article) { return Article.getDescription(); }
    };

    public static final Attribute<Article, Long> TIMESTAMP = new SimpleAttribute<Article, Long>("TIMESTAMP")
    {
        public Long getValue(Article Article) { return Article.getTimestamp(); }
    };
    public static final AttributeOrder<Article> byTimestampDesc = new AttributeOrder<Article>(TIMESTAMP, true);
    public static final AttributeOrder<Article> byTimestampAsc = new AttributeOrder<Article>(TIMESTAMP, false);
    public static final Attribute<Article, ArticleState> STATE = new SimpleAttribute<Article, ArticleState>("STATE")
    {
        public ArticleState getValue(Article Article) { return Article.getState(); }
    };
}
