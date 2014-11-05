package me.wener.telletsj.data.impl;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;
import me.wener.telletsj.data.Article;
import me.wener.telletsj.data.ArticleState;

@Data
@Accessors(chain = true, fluent = true)
public class ArticleBuilder implements Serializable
{
    private String title;
    private String link;
    private String sha;
    private String content;
    private String description;
    private Long timestamp;
    private ArticleState state;

    protected ArticleBuilder()
    { }

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

    public Article build()
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
}
