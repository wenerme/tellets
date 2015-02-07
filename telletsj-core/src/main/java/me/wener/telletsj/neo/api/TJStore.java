package me.wener.telletsj.neo.api;

import java.util.Map;
import java.util.Set;

public interface TJStore
{
    Map<String, Object> getMeta(String id);

    TJStore setMeta(String id, Map<String, Object> meta);

    boolean exists(String id);

    String getContent(String id);

    TJStore setContent(String id, String content);

    /**
     * @return 所有的标签列表
     */
    Set<String> getTags();

    /**
     * @return 所有的分类列表
     */
    Set<String> getCategories();

    Set<String> getIdByTag(String tag);

    Set<String> getIdByCategory(String category);
}
