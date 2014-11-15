package me.wener.telletsj.data.impl;

import com.google.common.eventbus.EventBus;
import java.util.Collection;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import me.wener.telletsj.data.Article;
import me.wener.telletsj.data.ArticleInfo;
import me.wener.telletsj.data.Category;
import me.wener.telletsj.data.DataService;
import me.wener.telletsj.data.Tag;

public class DataServiceAdapter implements DataService
{
    @Override
    public Article store(@Nonnull Article article, @Nonnull ArticleInfo info)
    {
        return null;
    }

    @Nullable
    @Override
    public Article store(Article article)
    {
        return null;
    }

    @Override
    public boolean store(Tag tag)
    {
        return false;
    }

    @Override
    public boolean store(Category category)
    {
        return false;
    }

    @Override
    public boolean store(ArticleInfo info)
    {
        return false;
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
    public Collection<Category> getRootCategories()
    {
        return null;
    }

    @Override
    public Collection<Category> getChildrenOf(Category c)
    {
        return null;
    }

    @Override
    public Collection<Category> getChildrenRecursiveOf(Category c)
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
    public Collection<Article> findArticleByTags(Set<Tag> tags, int offset, int limit)
    {
        return null;
    }

    @Override
    public Collection<Article> findArticleByCategories(Set<Category> categories, int offset, int limit)
    {
        return null;
    }


    @Override
    public
    @Nonnull
    ArticleInfo getArticleInfo(@Nonnull Article article)
    {
        return null;
    }

    @Override
    public ArticleBuilder createArticleBuilder()
    {
        return null;
    }

    @Override
    public Collection<Article> getArticleOrderByDate(int offset, int limit, boolean descending)
    {
        return null;
    }

    @Override
    public Tag findOrCreateTag(String name)
    {
        return null;
    }

    @Override
    public Category findOrCreateCategory(String name)
    {
        return null;
    }

}
