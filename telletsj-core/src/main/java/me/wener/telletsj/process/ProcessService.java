package me.wener.telletsj.process;

import me.wener.telletsj.article.Article;
import me.wener.telletsj.collect.SourceContent;

/**
 * 处理服务接口
 */
public interface ProcessService
{
    MetaData process(String content) throws ProcessException;

    void register(MetaProcessor processor);

    void register(Class<? extends MetaProcessor> clazz);
}
