package me.wener.telletsj.process;

import me.wener.telletsj.article.Article;
import me.wener.telletsj.collect.SourceContent;

/**
 * 处理服务接口
 */
public interface ProcessService
{
    Article process(SourceContent content) throws ProcessException;
    void register(ArticleProcessor processor);
    void register(Class<? extends ArticleProcessor> clazz);
}
