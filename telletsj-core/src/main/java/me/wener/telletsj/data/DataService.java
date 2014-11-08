package me.wener.telletsj.data;

import com.google.common.eventbus.EventBus;
import java.util.Collection;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import me.wener.telletsj.data.impl.ArticleBuilder;

/**
 * 文章服务
 */
public interface DataService
{
    @Nullable
    Article store(Article article, ArticleInfo info);

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

    Collection<Category> getRootCategories();

    Collection<Category> getChildrenOf(Category c);

    Collection<Category> getChildrenRecursiveOf(Category c);

    /**
     * @return 查找标签, 没有找到返回 {@code null}
     */
    @Nullable
    Tag findTag(String name);

    /**
     * @return 查找分类, 没有找到返回 {@code null}
     */
    @Nullable
    Category findCategory(String name);

    /**
     * @param sha 进行查找的 SHA 值
     * @return 没有找到返回 {@code null}
     */
    @Nullable
    Article getArticleBySha(String sha);

    /**
     * @param link 进行查找的 Link 值
     * @return 没有找到返回 {@code null}
     */
    @Nullable
    Article getArticleByLink(String link);

    Collection<Article> getArticleByTags(Set<Tag> tags, int offset, int limit);

    Collection<Article> getArticleByCategories(Set<Category> tags, int offset, int limit);

    @Nonnull
    ArticleInfo getArticleInfo(@Nonnull Article article);

    /**
     * @return 获取一个文章对象的Builder, 构建出来的Article应该是实现使用的Article
     */
    ArticleBuilder createArticleBuilder();

    /**
     * @param offset     偏移量,从<b>0</b>开始
     * @param limit      数量限制,<b>-1</b>表示没有限制
     * @param descending 降序
     * @return 返回范围内的文章
     */
    Iterable<Article> getArticleOrderByDate(int offset, int limit, boolean descending);

    Tag createTag();

    Category createCategory();
}
