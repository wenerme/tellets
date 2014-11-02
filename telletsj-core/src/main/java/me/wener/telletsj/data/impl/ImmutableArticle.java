package me.wener.telletsj.data.impl;

import com.mysema.query.annotations.QueryEntity;
import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;
import me.wener.telletsj.data.Article;
import me.wener.telletsj.data.ArticleState;

/**
 * 文章实体类
 */
@Data
@Setter(AccessLevel.NONE)
@QueryEntity
@Immutable
@Builder
public class ImmutableArticle implements Article
{

    @NotNull
    private final String title;

    private final String link;

    @NotNull
    private final String sha;

    @NotNull
    private final String content;

    private final String description;

    private final long timestamp;

    @NotNull
    private final ArticleState state;

    @Override
    public String getId()
    {
        return link;
    }
}
