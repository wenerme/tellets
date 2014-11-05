package me.wener.telletsj.data;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;

/**
 * 文章的附加信息
 */
public interface ArticleInfo extends Serializable
{
    /**
     * @return 与文章对应的 sha 值
     */
    @Nonnull
    String getSha();

    /**
     * @return 获取标签, 标签应该是有序的
     */
    @Nonnull
    Set<Tag> getTags();

    /**
     * @return 获取文章分类, 文章分类应该是有序的
     */
    @Nonnull
    Set<Category> getCategories();

    /**
     * @return 获取特性集合
     */
    @Nonnull
    Set<String> getFeatures();

    /**
     * @return 获取其他元数据
     */
    @Nonnull
    Map<String, String> getMeta();

}
