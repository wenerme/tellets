package me.wener.telletsj.data.impl;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import me.wener.telletsj.data.Article;
import me.wener.telletsj.data.ArticleInfo;
import me.wener.telletsj.data.ArticleState;
import me.wener.telletsj.data.Category;
import me.wener.telletsj.data.Tag;

@Data
@Accessors(chain = true, fluent = true)
public class ArticleBuilder implements Serializable
{
    private final Set<Tag> tags = Sets.newLinkedHashSet();
    private final Set<Category> categories = Sets.newLinkedHashSet();
    private final Set<String> features = Sets.newLinkedHashSet();
    private final Map<String, String> meta = Maps.newHashMap();
    private String title;
    private String link;
    private String sha;
    private String content;
    private String description;
    private Long timestamp;
    private ArticleState state;

    protected ArticleBuilder()
    { }

    public ArticleBuilder addTags(Iterable<Tag> tags)
    {
        Iterables.addAll(this.tags, tags);
        return this;
    }


    public ArticleBuilder from(ArticleInfo info)
    {
        tags.clear();
        categories.clear();
        features.clear();
        meta.clear();

        tags.addAll(info.getTags());
        categories.addAll(info.getCategories());
        features.addAll(info.getFeatures());
        meta.putAll(info.getMeta());

        return this;
    }

    public ArticleBuilder from(Article article)
    {
        title = article.getTitle();
        link = article.getLink();
        sha = article.getSha();
        content = article.getContent();
        description = article.getDescription();
        timestamp = article.getTimestamp();
        return this;
    }

    public Article article()
    {
        return ImmutableArticle
                .builder()
                .title(title)
                .link(link)
                .sha(sha)
                .content(content)
                .description(description)
                .state(state)
                .timestamp(timestamp)
                .build();

    }

    public ArticleInfo info()
    {
        ArticleInfoVO info = new ArticleInfoVO(sha);
        info.getCategories().addAll(categories);
        info.getFeatures().addAll(features);
        info.getMeta().putAll(meta);
        info.getTags().addAll(tags);
        return info;
    }
}
