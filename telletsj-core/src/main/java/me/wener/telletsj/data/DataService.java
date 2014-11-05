package me.wener.telletsj.data;

import com.google.common.eventbus.EventBus;
import java.util.Collection;
import java.util.Set;

/**
 * 文章服务
 */
public interface DataService
{
    Article store(Article article);

    /**
     * 将会使用该文章的 sha 来判断移除的文章
     */
    boolean remove(Article article);

    /**
     * @return 当对内容操作的时候会从该EventBus触发事件
     */
    EventBus getEventBus();
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


    Collection<Article> getArticleByTags(Set<Tag> tags, int offset, int limit);

    Collection<Article> getArticleByCategories(Set<Category> tags, int offset, int limit);

    /**
     *
     * @param offset    偏移量,从<b>0</b>开始
     * @param limit     数量限制,<b>-1</b>表示没有限制
     * @return 返回范围内的文章
     */
    Collection<Article> getArticleOrderByDate(int offset, int limit);
}
