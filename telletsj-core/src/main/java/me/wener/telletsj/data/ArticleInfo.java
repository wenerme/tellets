package me.wener.telletsj.data;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ArticleInfo
{
    /**
     * 关联的文章SHA值
     */
    @NotNull
    private final String sha;
    /**
     * 文章标签
     */
    private final Set<Tag> tags = Sets.newHashSet();
    /**
     * 文章分类
     */
    private final Set<Category> categories = Sets.newHashSet();
    /**
     * 在文章内容中启用的一些特性
     */
    private final Set<String> features = Sets.newHashSet();
    /**
     * 文章相关的其他元数据
     */
    private final Map<String, String> meta = Maps.newHashMap();

    /**
     * 作者
     */
    private String author;
}
