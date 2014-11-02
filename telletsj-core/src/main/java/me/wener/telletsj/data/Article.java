package me.wener.telletsj.data;

/**
 * 文章接口,作为一个文章该有的主要内容
 */
public interface Article extends Identifiable
{
    /**
     * @return 与 {@link #getLink} 相同
     */
    @Override
    String getId();
    /**
     * 文章标题
     */
    String getTitle();
    /**
     * 文章链接
     */
    String getLink();
    /**
     * 文章SHA1值
     */
    String getSha();
    /**
     * 文章内容
     */
    String getContent();
    /**
     * 文章描述
     */
    String getDescription();
    /**
     * 文章发布时间戳
     */
    long getTimestamp();
    /**
     * 文章状态
     */
    ArticleState getState();
}
