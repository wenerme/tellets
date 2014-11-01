package me.wener.telletsj.process;

/**
 * 处理服务接口
 */
public interface ProcessService
{
    ContentInfo process(String content) throws ProcessException;

    void register(MetaProcessor processor);

    void register(Class<? extends MetaProcessor> clazz);
}
