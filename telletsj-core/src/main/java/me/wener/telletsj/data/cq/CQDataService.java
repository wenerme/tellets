package me.wener.telletsj.data.cq;

import static com.google.common.base.Preconditions.*;
import static com.googlecode.cqengine.query.QueryFactory.*;
import static me.wener.telletsj.data.cq.CQ.isNull;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.eventbus.EventBus;
import com.googlecode.cqengine.CQEngine;
import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.index.hash.HashIndex;
import com.googlecode.cqengine.index.navigable.NavigableIndex;
import com.googlecode.cqengine.index.suffix.SuffixTreeIndex;
import com.googlecode.cqengine.query.option.AttributeOrder;
import com.googlecode.cqengine.resultset.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.wener.telletsj.data.Article;
import me.wener.telletsj.data.ArticleInfo;
import me.wener.telletsj.data.Category;
import me.wener.telletsj.data.Tag;
import me.wener.telletsj.data.event.RemoveEvent;
import me.wener.telletsj.data.event.StoreEvent;
import me.wener.telletsj.data.impl.ArticleBuilder;
import me.wener.telletsj.data.impl.CategoryVO;
import me.wener.telletsj.data.impl.DataServiceAdapter;
import me.wener.telletsj.data.impl.TagVo;
import me.wener.telletsj.util.event.Events;

@EqualsAndHashCode(callSuper = false)
@Data
public class CQDataService extends DataServiceAdapter
{

    private final IndexedCollection<ArticleVO> articles;
    private final IndexedCollection<Tag> tags;
    private final IndexedCollection<Category> categories;
    private final EventBus eventBus;

    public CQDataService()
    {
        articles = CQEngine.newInstance();
        articles.addIndex(SuffixTreeIndex.onAttribute(CQArticle.TITLE));
        articles.addIndex(SuffixTreeIndex.onAttribute(CQArticle.CONTENT));
        articles.addIndex(HashIndex.onAttribute(CQArticle.LINK));
        articles.addIndex(NavigableIndex.onAttribute(CQArticle.TIMESTAMP));

        tags = CQEngine.newInstance();
        tags.addIndex(HashIndex.onAttribute(CQInfo.TAG_NAME));

        categories = CQEngine.newInstance();
        categories.addIndex(HashIndex.onAttribute(CQInfo.CATEGORY_NAME));

        eventBus = Events.createWithExceptionDispatch();
    }

    @Override
    public Article getArticleByLink(String link)
    {
        return oneByEQ(link, CQArticle.LINK);
    }

    @Override
    public ArticleVO getArticleBySha(String sha)
    {
        return oneByEQ(sha, CQArticle.SHA);
    }

    private ArticleVO oneByEQ(String val, Attribute<ArticleVO, String> attr)
    {
        ResultSet<ArticleVO> resultSet = articles.retrieve(equal(attr, val));
        if (resultSet.size() == 0)
            return null;

        checkArgument(resultSet.size() == 1, "Find multi article with %s=%s", attr.getAttributeName(), val);
        return resultSet.uniqueResult();
    }

    @Override
    public Article store(@Nonnull Article article, @Nonnull ArticleInfo info)
    {
        eventBus.post(new StoreEvent(article));

        checkNotNull(article.getTitle());
        checkNotNull(article.getLink());
        checkNotNull(article.getTimestamp());
        checkNotNull(article.getContent());
        checkNotNull(article.getSha());
        checkNotNull(article.getState());
        checkArgument(article.getSha().equals(info.getSha()));

        ArticleVO vo;
        if (article instanceof ArticleVO && article == info)
            vo = (ArticleVO) article;
        else
            vo = new ArticleVO(article, info);

        if (articles.contains(vo))
            return null;

        Article old = getArticleBySha(article.getSha());
        articles.add(vo);

        tags.addAll(vo.getTags());
        categories.addAll(vo.getCategories());

        return old;
    }

    @Nullable
    @Override
    public Article store(Article article)
    {
        eventBus.post(new StoreEvent(article));

        return store(article, vo(article));
    }

    @Override
    public boolean store(ArticleInfo info)
    {
        eventBus.post(new StoreEvent(info));

        checkArgument(articles.contains(vo(info)), "Wrong info object.");
        return true;
    }

    @Override
    public boolean store(Category category)
    {
        eventBus.post(new StoreEvent(category));

        Category old = findCategory(category.getName());
        if (old == category || (old != null && old.equals(category)))
        {
            return false;
        }
//        if (!categories.contains(category))
//        {
//            checkArgument(old == null, "Category with same name already exists.");
//        }
        return categories.add(category);
    }

    @Override
    public boolean store(Tag tag)
    {
        eventBus.post(new StoreEvent(tag));

        Tag old = findTag(tag.getName());
        if (old == tag || (old != null && old.equals(tag)))
        {
            return false;
        }
//        if (!tags.contains(tag))
//        {
//            checkArgument(findCategory(tag.getName()) == null, "Tag with same name already exists.");
//        }
        return tags.add(tag);
    }

    @Override
    public boolean remove(Article article)
    {
        Article articleBySha = getArticleBySha(article.getSha());

        eventBus.post(new RemoveEvent(articleBySha));

        return articles.remove(vo(articleBySha));
    }

    @Nonnull
    @Override
    public ArticleInfo getArticleInfo(@Nonnull Article article)
    {
        return vo(article);
    }

    @SuppressWarnings("ConstantConditions")
    private ArticleVO vo(Object a)
    {
        Preconditions.checkArgument(a instanceof ArticleVO, "非内部实现对象");
        return (ArticleVO) a;
    }


    @Override
    public ArticleBuilder createArticleBuilder()
    {
        return new ArticleVOBuilder();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Article> getArticleOrderByDate(int offset, int limit, boolean descending)
    {
        ResultSet<ArticleVO> rs = articles
                .retrieve(CQ
                        .any(CQArticle.TIMESTAMP), queryOptions(orderBy(new AttributeOrder<>(CQArticle.TIMESTAMP, descending))));
        return limit(offset, limit, rs);
    }

    private List<Article> limit(int offset, int limit, Iterator<ArticleVO> iterator)
    {
        Iterators.advance(iterator, offset);
        return Lists.<Article>newArrayList(Iterators.limit(iterator, limit));
    }

    private List<Article> limit(int offset, int limit, ResultSet<ArticleVO> rs)
    {
        return limit(offset, limit, rs.iterator());
    }

    private List<Article> limit(int offset, int limit, com.googlecode.cqengine.query.Query<ArticleVO> query)
    {
        return limit(offset, limit, articles.retrieve(query));
    }

    @Override
    public Category findCategory(String name)
    {
        ResultSet<Category> rs = categories
                .retrieve(or(equal(CQInfo.CATEGORY_NAME, name), equal(CQInfo.CATEGORY_ALIASES, name)));
        return oneOrNull(rs);
    }

    private <T> T oneOrNull(ResultSet<T> rs)
    {
        if (rs.isEmpty())
            return null;
        return rs.uniqueResult();
    }

    @Override
    public Tag findTag(String name)
    {
        return oneOrNull(tags.retrieve(or(equal(CQInfo.TAG_NAME, name), equal(CQInfo.TAG_ALIASES, name))));
    }

    @Override
    public Category findOrCreateCategory(String name)
    {
        Category category = findCategory(name);
        if (category == null)
        {
            category = new CategoryVO().setName(name);
            categories.add(category);
        }
        return category;
    }

    @Override
    public Tag findOrCreateTag(String name)
    {
        Tag tag = findTag(name);
        if (tag == null)
        {
            tag = new TagVo().setName(name);
            tags.add(tag);
        }
        return tag;
    }

    @Override
    public Collection<Article> findArticleByCategories(Set<Category> categories, int offset, int limit)
    {
        return limit(offset, limit, in(CQInfo.CATEGORIES, categories));
    }

    @Override
    public Collection<Article> findArticleByTags(Set<Tag> tags, int offset, int limit)
    {
        return limit(offset, limit, in(CQInfo.TAGS, tags));
    }

    @Override
    public Collection<Category> getRootCategories()
    {
        return Lists.newArrayList(categories.retrieve(isNull(CQInfo.CATEGORY_PARENT)));
    }

    @Override
    public Collection<Category> getChildrenOf(Category c)
    {
        return Lists.newArrayList(categories.retrieve(equal(CQInfo.CATEGORY_PARENT, c)));
    }

    @Override
    public Collection<Category> getChildrenRecursiveOf(Category c)
    {
        Set<Category> all = Sets.newHashSet();
        Collection<Category> children = getChildrenOf(c);
        all.addAll(children);
        for (Category child : children)
        {
            all.addAll(getChildrenRecursiveOf(child));
        }
        return all;
    }
}
