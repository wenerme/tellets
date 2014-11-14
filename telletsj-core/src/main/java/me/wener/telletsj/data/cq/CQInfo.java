package me.wener.telletsj.data.cq;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import java.util.Set;
import me.wener.telletsj.data.ArticleInfo;
import me.wener.telletsj.data.Category;
import me.wener.telletsj.data.Tag;

class CQInfo
{
    public static final Attribute<ArticleInfo, Set<Tag>> TAGS = new SimpleAttribute<ArticleInfo, Set<Tag>>("TAGS")
    {
        public Set<Tag> getValue(ArticleInfo ArticleInfo) { return ArticleInfo.getTags(); }
    };

    public static final Attribute<ArticleInfo, Set<Category>> CATEGORIES = new SimpleAttribute<ArticleInfo, Set<Category>>("CATEGORIES")
    {
        public Set<Category> getValue(ArticleInfo ArticleInfo) { return ArticleInfo.getCategories(); }
    };
    public static final Attribute<ArticleInfo, Set<Category>> ALIASES = new SimpleAttribute<ArticleInfo, Set<Category>>("CATEGORIES")
    {
        public Set<Category> getValue(ArticleInfo ArticleInfo) { return ArticleInfo.getCategories(); }
    };

    public static final Attribute<ArticleInfo, String> SHA = new SimpleAttribute<ArticleInfo, String>("SHA")
    {
        public String getValue(ArticleInfo Article) { return Article.getSha(); }
    };
}
