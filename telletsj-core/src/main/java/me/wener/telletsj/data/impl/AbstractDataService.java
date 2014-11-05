package me.wener.telletsj.data.impl;

import com.google.common.eventbus.EventBus;
import java.util.Collection;
import java.util.Set;
import me.wener.telletsj.data.Article;
import me.wener.telletsj.data.Category;
import me.wener.telletsj.data.DataService;
import me.wener.telletsj.data.Tag;

public class AbstractDataService implements DataService
{
    @Override
    public Article store(Article article)
    {
        return null;
    }

    @Override
    public boolean remove(Article article)
    {
        return false;
    }

    @Override
    public EventBus getEventBus()
    {
        return null;
    }

    @Override
    public Collection<Tag> getTags()
    {
        return null;
    }

    @Override
    public Collection<Category> getCategories()
    {
        return null;
    }

    @Override
    public Tag findTag(String name)
    {
        return null;
    }

    @Override
    public Category findCategory(String name)
    {
        return null;
    }

    @Override
    public Article getArticleBySha(String sha)
    {
        return null;
    }

    @Override
    public Article getArticleByLink(String link)
    {
        return null;
    }

    @Override
    public Collection<Article> getArticleByTags(Set<Tag> tags, int offset, int limit)
    {
        return null;
    }

    @Override
    public Collection<Article> getArticleByCategories(Set<Category> tags, int offset, int limit)
    {
        return null;
    }

    @Override
    public Collection<Article> getArticleOrderByDate(int offset, int limit)
    {
        return null;
    }
}
