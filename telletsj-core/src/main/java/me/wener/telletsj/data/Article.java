package me.wener.telletsj.data;

import java.io.Serializable;
import javax.annotation.Nonnull;

/**
 * 文章接口,作为一个文章该有的主要内容
 */
public interface Article extends Serializable
{
    /**
     * 文章标题
     */
    @Nonnull
    String getTitle();

    /**
     * 文章链接
     */
    @Nonnull
    String getLink();

    /**
     * 文章SHA1值
     */
    @Nonnull
    String getSha();

    /**
     * 文章内容
     */
    @Nonnull
    String getContent();

    /**
     * 文章描述
     */
    String getDescription();

    /**
     * 文章发布时间戳
     */
    @Nonnull
    Long getTimestamp();

    /**
     * 文章状态
     */
    @Nonnull
    ArticleState getState();
}
