package me.wener.telletsj.collect;

/**
 * 收集服务
 */
public interface CollectionService
{
    void register(SourceProvider provider);

    Iterable<SourceContent> collect() throws CollectionException;
}
