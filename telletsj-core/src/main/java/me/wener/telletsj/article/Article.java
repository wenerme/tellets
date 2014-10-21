package me.wener.telletsj.article;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 文章实体类
 */
@Data
@Accessors(chain = true)
public class Article
{
    private final List<Tag> tags = Lists.newArrayList();
    private final List<Category> categories = Lists.newArrayList();
    private final List<String> features = Lists.newArrayList();
    /**
     * 文章相关的其他元数据
     */
    private final Map<String, String> meta = Maps.newHashMap();
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章链接
     */
    private String link;
    /**
     * 作者
     */
    private String author;
    /**
     * 文章发布日期
     */
    private Date date;
    /**
     * 文章状态,影响文章是否显示
     */
    private String state;

    public enum ArticleState
    {
        PUBLISH, DRAFT
    }
}
