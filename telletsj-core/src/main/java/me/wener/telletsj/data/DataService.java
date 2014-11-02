package me.wener.telletsj.data;

import java.util.Collection;
import java.util.Set;

/**
 * 文章服务
 */
public interface DataService
{
    DataService storeArticle(Article article);
    /**
     * @return 所有的标签列表
     */
    Collection<Tag> getTags();

    /**
     * @return 所有的分类列表
     */
    Collection<Category> getCategories();

    /**
     * @return 查找标签, 没有找到返回 {@code null}
     */
    Tag findTag(String name);

    /**
     * @return 查找分类, 没有找到返回 {@code null}
     */
    Category findCategory(String name);

    /**
     * @param sha 进行查找的 SHA 值
     * @return 没有找到返回 {@code null}
     */
    Article getArticleBySha(String sha);
    /**
     * @param link 进行查找的 Link 值
     * @return 没有找到返回 {@code null}
     */
    Article getArticleByLink(String link);

    /**
     *
     * @param offset    偏移量,从<b>0</b>开始
     * @param limit     数量限制,<b>-1</b>表示没有限制
     * @return 返回范围内的文章
     */
    Collection<Article> getArticles(int offset, int limit);

    Collection<Article> getArticlesByTags(Set<Tag> tags, int offset, int limit);

    Collection<Article> getArticlesByCategories(Set<Tag> tags, int offset, int limit);

    Collection<Article> getArticlesOrderByDate(int offset, int limit);
}
