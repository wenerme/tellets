package me.wener.telletsj.data.impl;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import me.wener.telletsj.data.ArticleInfo;
import me.wener.telletsj.data.Category;
import me.wener.telletsj.data.Tag;

@Data
@Accessors(chain = true)
public class ArticleInfoVO implements ArticleInfo
{
    /**
     * 关联的文章SHA值
     */
    private final String sha;
    /**
     * 文章标签
     */
    private final Set<Tag> tags = Sets.newLinkedHashSet();
    /**
     * 文章分类
     */
    private final Set<Category> categories = Sets.newLinkedHashSet();
    /**
     * 在文章内容中启用的一些特性
     */
    private final Set<String> features = Sets.newLinkedHashSet();
    /**
     * 文章相关的其他元数据
     */
    private final Map<String, String> meta = Maps.newHashMap();

    public ArticleInfoVO(String sha)
    {
        this.sha = sha;
    }
}
