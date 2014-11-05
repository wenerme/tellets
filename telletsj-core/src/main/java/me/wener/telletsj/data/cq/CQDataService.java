package me.wener.telletsj.data.cq;

import static com.googlecode.cqengine.query.QueryFactory.equal;

import com.google.common.base.Preconditions;
import com.googlecode.cqengine.CQEngine;
import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.index.hash.HashIndex;
import com.googlecode.cqengine.index.navigable.NavigableIndex;
import com.googlecode.cqengine.index.suffix.SuffixTreeIndex;
import com.googlecode.cqengine.resultset.ResultSet;
import me.wener.telletsj.data.Article;
import me.wener.telletsj.data.impl.AbstractDataService;

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
        ResultSet<Article> resultSet = articles.retrieve(equal(CQArticle.LINK, link));
        Preconditions.checkState(resultSet.size() == 1, "Find multi article with same link");
        return resultSet.uniqueResult();
    }
}
