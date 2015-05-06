package me.wener.telletsj.api;

import java.util.Map;
import java.util.Set;

/**
 * 数据存储
 */
public interface TJStore
{
    Map<String, Object> getMeta(String id);

    TJStore storeMeta(String id, Map<String, Object> meta);

    boolean exists(String id);

    String getContent(String id);

    TJStore storeContent(String id, String content);

    /**
     * @return 所有的标签列表
     */
    Set<String> getTags();

    /**
     * @return 所有的分类列表
     */
    Set<String> getCategories();

    Set<String> findByTag(String tag);

    Set<String> findByCategory(String category);

    Iterable<String> findAll();
}
