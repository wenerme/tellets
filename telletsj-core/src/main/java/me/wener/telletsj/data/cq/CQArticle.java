package me.wener.telletsj.data.cq;


import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.attribute.SimpleNullableAttribute;
import com.googlecode.cqengine.query.option.AttributeOrder;
import me.wener.telletsj.data.ArticleState;

class CQArticle
{
    public static final Attribute<ArticleVO, String> TITLE = new SimpleAttribute<ArticleVO, String>("TITLE")
    {
        public String getValue(ArticleVO ArticleVO) { return ArticleVO.getTitle(); }
    };

    public static final Attribute<ArticleVO, String> LINK = new SimpleNullableAttribute<ArticleVO, String>("LINK")
    {
        public String getValue(ArticleVO ArticleVO) { return ArticleVO.getLink(); }
    };

    public static final Attribute<ArticleVO, String> SHA = new SimpleAttribute<ArticleVO, String>("SHA")
    {
        public String getValue(ArticleVO ArticleVO) { return ArticleVO.getSha(); }
    };

    public static final Attribute<ArticleVO, String> CONTENT = new SimpleAttribute<ArticleVO, String>("CONTENT")
    {
        public String getValue(ArticleVO ArticleVO) { return ArticleVO.getContent(); }
    };


    public static final Attribute<ArticleVO, String> DESCRIPTION = new SimpleNullableAttribute<ArticleVO, String>("DESCRIPTION")
    {
        public String getValue(ArticleVO ArticleVO) { return ArticleVO.getDescription(); }
    };

    public static final Attribute<ArticleVO, Long> TIMESTAMP = new SimpleAttribute<ArticleVO, Long>("TIMESTAMP")
    {
        public Long getValue(ArticleVO ArticleVO) { return ArticleVO.getTimestamp(); }
    };
    public static final AttributeOrder<ArticleVO> byTimestampDesc = new AttributeOrder<ArticleVO>(TIMESTAMP, true);
    public static final AttributeOrder<ArticleVO> byTimestampAsc = new AttributeOrder<ArticleVO>(TIMESTAMP, false);
    public static final Attribute<ArticleVO, ArticleState> STATE = new SimpleAttribute<ArticleVO, ArticleState>("STATE")
    {
        public ArticleState getValue(ArticleVO ArticleVO) { return ArticleVO.getState(); }
    };
}
