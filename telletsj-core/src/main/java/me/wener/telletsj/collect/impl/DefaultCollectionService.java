package me.wener.telletsj.collect.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.net.URI;
import java.util.List;
import java.util.Set;
import me.wener.telletsj.collect.CollectSource;
import me.wener.telletsj.collect.CollectionException;
import me.wener.telletsj.collect.CollectionService;
import me.wener.telletsj.collect.SourceProvider;

public class DefaultCollectionService implements CollectionService
{
    private final Set<URI> sourceUris = Sets.newConcurrentHashSet();
    private final Set<CollectSource> sources = Sets.newConcurrentHashSet();
    private final SourceProviderManager provider;

    public DefaultCollectionService(SourceProviderManager provider) {this.provider = provider;}

    @Override
    public void register(SourceProvider provider)
    {
        this.provider.addProvider(provider);
    }

    @Override
    public Iterable<SourceContent> collect(URI uri) throws CollectionException
    {
        return getSource(uri).collect();
    }

    @Override
    public Iterable<SourceContent> collect(Iterable<URI> uris) throws CollectionException
    {
        Iterable<SourceContent> contents = Lists.newArrayList();
        for (URI uri : uris)
        {
            CollectSource source = getSource(uri);
            if (source.isChanged())
                contents = Iterables.concat(contents, source.collect());
        }
        return contents;
    }

    @Override
    public CollectSource getSource(URI uri)
    {
        return provider.getSource(uri);
    }

    @Override
    public SourceProvider getProvider(URI uri)
    {
        return provider.getSourceProvider(uri);
    }

    @Override
    public SourceContent getContent(URI uri) throws CollectionException
    {
        List<SourceContent> contents = Lists.newArrayList(collect(uri));
        Preconditions.checkArgument(contents.size() != 0, "Content for uri not found: " + uri);
        Preconditions.checkArgument(contents.size() == 1, "More than one content for uri: " + uri);

        return contents.get(0);
    }
}
