package me.wener.telletsj.data;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true, fluent = true)
public abstract class ArticleBuilder implements Serializable
{
    private String title;

    private String link;

    private String sha;

    private String content;

    private String description;

    private Long timestamp;

    public abstract Article build();
}
