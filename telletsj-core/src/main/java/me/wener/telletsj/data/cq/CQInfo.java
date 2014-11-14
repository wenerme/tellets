package me.wener.telletsj.data.cq;

import com.google.common.collect.Lists;
import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.MultiValueAttribute;
import com.googlecode.cqengine.attribute.ReflectiveAttribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import java.util.List;
import me.wener.telletsj.data.Category;
import me.wener.telletsj.data.Tag;

class CQInfo
{
    public static final Attribute<ArticleVO, Tag> TAGS = new MultiValueAttribute<ArticleVO, Tag>("TAGS")
    {
        public List<Tag> getValues(ArticleVO object)
        {
            return Lists.newArrayList(object.getTags());
        }
    };

    public static final Attribute<ArticleVO, Category> CATEGORIES = new MultiValueAttribute<ArticleVO, Category>("CATEGORIES")
    {
        @Override
        public List<Category> getValues(ArticleVO object)
        {
            return Lists.newArrayList(object.getCategories());
        }
    };

    public static final Attribute<ArticleVO, String> SHA = new SimpleAttribute<ArticleVO, String>("SHA")
    {
        public String getValue(ArticleVO Article) { return Article.getSha(); }
    };
    public static final Attribute<Tag, String> TAG_NAME = new SimpleAttribute<Tag, String>("NAME")
    {
        public String getValue(Tag Tag) { return Tag.getName(); }
    };
    public static final Attribute<Category, String> CATEGORY_NAME = new SimpleAttribute<Category, String>("NAME")
    {
        public String getValue(Category Tag) { return Tag.getName(); }
    };
    public static final Attribute<Category, Category> CATEGORY_PARENT = ReflectiveAttribute
            .forField(Category.class, Category.class, "parent");


    public static final Attribute<Tag, String> TAG_ALIASES = new MultiValueAttribute<Tag, String>("ALIASES")
    {
        public List<String> getValues(Tag Tag) { return Lists.newArrayList(Tag.aliases()); }
    };
    public static final Attribute<Category, String> CATEGORY_ALIASES = new MultiValueAttribute<Category, String>("ALIASES")
    {
        public List<String> getValues(Category Tag) { return Lists.newArrayList(Tag.aliases()); }
    };
}
