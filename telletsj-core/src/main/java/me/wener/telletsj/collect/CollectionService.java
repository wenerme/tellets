package me.wener.telletsj.collect;

import java.net.URI;

/**
 * 收集服务接口
 */
public interface CollectionService
{
    void register(SourceProvider provider);

    Iterable<SourceContent> collect(URI uri) throws CollectionException;

    Iterable<SourceContent> collect(Iterable<URI> uris) throws CollectionException;

    CollectSource getSource(URI uri);

    SourceProvider getProvider(URI uri);

    SourceContent getContent(URI uri) throws CollectionException;
}
