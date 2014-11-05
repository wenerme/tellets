package me.wener.telletsj.data;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public interface ArticleInfo extends Serializable
{
    /**
     * @return 与文章对应的 sha 值
     */
    String getSha();

    /**
     * @return 获取标签, 标签应该是有序的
     */
    Set<String> getTags();

    /**
     * @return 获取文章分类, 文章分类应该是有序的
     */
    Set<String> getCategories();

    /**
     * @return 获取特性集合
     */
    Set<String> getFeatures();

    /**
     * @return 获取其他元数据
     */
    Map<String, String> getMeta();

    String getAuthor();
}
