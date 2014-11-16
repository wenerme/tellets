package me.wener.telletsj.data;

/**
 * 文章状态
 */
public enum ArticleState
{
    /**
     * 发布状态
     */
    PUBLISH,
    /**
     * 手稿状态
     */
    DRAFT,
    /**
     * 对于未知的文章状态,应该在文章的meta里放入原有的状态信息.
     */
    UNKNOWN;

    public static ArticleState fromString(String str)
    {
        return valueOf(str.toUpperCase().trim());
    }
}
