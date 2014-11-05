package me.wener.telletsj.data.impl;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Builder;
import me.wener.telletsj.data.Article;
import me.wener.telletsj.data.ArticleState;

/**
 * 文章实体类
 */
@Data
@Setter(AccessLevel.NONE)
@Immutable
@Builder
public class ImmutableArticle implements Article
{

    @Nonnull
    private final String title;

    private final String link;

    @Nonnull
    private final String sha;

    @Nonnull
    private final String content;

    private final String description;

    private final long timestamp;


    @Nonnull
    private final ArticleState state;

    @Override
    public String getId()
    {
        return link;
    }
}
