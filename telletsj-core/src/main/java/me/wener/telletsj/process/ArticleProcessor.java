package me.wener.telletsj.process;

import me.wener.telletsj.article.Article;
import me.wener.telletsj.collect.SourceContent;

/**
 * 将内容处理为文章对象
 */
public interface ArticleProcessor
{

    /**
     *
     * @return 如果无法处理,则返回 {@code null}
     * @throws ProcessException 在处理过程中发生异常
     */
    Article process(SourceContent content) throws ProcessException;
}
