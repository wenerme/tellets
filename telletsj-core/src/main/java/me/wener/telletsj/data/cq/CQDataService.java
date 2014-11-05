package me.wener.telletsj.data.cq;

import static com.google.common.base.Preconditions.*;
import static com.googlecode.cqengine.query.QueryFactory.*;

import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.googlecode.cqengine.CQEngine;
import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.index.hash.HashIndex;
import com.googlecode.cqengine.index.navigable.NavigableIndex;
import com.googlecode.cqengine.index.suffix.SuffixTreeIndex;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.AttributeOrder;
import com.googlecode.cqengine.query.simple.SimpleQuery;
import com.googlecode.cqengine.resultset.ResultSet;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import me.wener.telletsj.data.Article;
import me.wener.telletsj.data.ArticleInfo;
import me.wener.telletsj.data.impl.AbstractDataService;
import me.wener.telletsj.data.impl.ArticleBuilder;

public class CQDataService extends AbstractDataService
{

    private final IndexedCollection<Article> articles;

    public CQDataService()
    {
        articles = CQEngine.newInstance();
        articles.addIndex(SuffixTreeIndex.onAttribute(CQArticle.TITLE));
        articles.addIndex(SuffixTreeIndex.onAttribute(CQArticle.CONTENT));
        articles.addIndex(HashIndex.onAttribute(CQArticle.LINK));
        articles.addIndex(NavigableIndex.onAttribute(CQArticle.TIMESTAMP));
    }

    @Override
    public Article getArticleByLink(String link)
    {
        return oneByEQ(link, CQArticle.LINK);
    }

    @Override
    public Article getArticleBySha(String sha)
    {
        return oneByEQ(sha, CQArticle.SHA);
    }

    private Article oneByEQ(String val, Attribute<Article, String> attr)
    {
        ResultSet<Article> resultSet = articles.retrieve(equal(attr, val));
        if (resultSet.size() == 0)
            return null;

        Preconditions.checkState(resultSet.size() == 1, "Find multi article with " + attr.getAttributeName());
        return resultSet.uniqueResult();
    }

    @Override
    public Article store(@Nonnull Article article, @Nonnull ArticleInfo info)
    {
        checkNotNull(article.getTitle());
        checkNotNull(article.getLink());
        checkNotNull(article.getTimestamp());
        checkNotNull(article.getContent());
        checkNotNull(article.getSha());
        checkNotNull(article.getState());
        checkArgument(article.getSha().equals(info.getSha()));

        if (articles.contains(article))
            return null;

        Article old = getArticleBySha(article.getSha());

        if (article instanceof ArticleVO)
            articles.add(article);
        else
            articles.add(new ArticleVO(article, info));

        return old;
    }


    @Override
    public boolean remove(Article article)
    {
        Article articleBySha = getArticleBySha(article.getSha());
        return articles.remove(articleBySha);
    }

    @Nonnull
    @Override
    public ArticleInfo getArticleInfo(@Nonnull Article article)
    {
        return vo(article);
    }

    @SuppressWarnings("ConstantConditions")
    private ArticleVO vo(Article a)
    {
        Preconditions.checkArgument(a instanceof ArticleVO, "非内部实现对象");
        return (ArticleVO) a;
    }

    @Override
    public ArticleBuilder createArticleBuilder()
    {
        return new ArticleVOBuilder();
    }

    @Override
    public Iterable<Article> getArticleOrderByDate(int offset, int limit, boolean descending)
    {
        ResultSet<Article> rs = articles.retrieve(any(CQArticle.TIMESTAMP),queryOptions(orderBy(new AttributeOrder<>(CQArticle.TIMESTAMP, descending))));
        Iterator<Article> iterator = rs.iterator();
        Iterators.advance(iterator, offset);
        return Lists.newArrayList(Iterators.limit(iterator, limit));
    }

    private <T,A> Query<T> any(Attribute<T, A> attribute)
    {
        return new SimpleQuery<T,A>(attribute)
        {

            @Override
            protected boolean matchesSimpleAttribute(SimpleAttribute<T, A> attribute, T object)
            {
                return true;
            }

            @Override
            protected boolean matchesNonSimpleAttribute(Attribute<T, A> attribute, T object)
            {
                return true;
            }

            @Override
            protected int calcHashCode()
            {
                return 0;
            }
        };
    }
}
