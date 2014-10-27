package me.wener.telletsj.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import java.net.URI;
import java.util.Set;
import javax.inject.Inject;
import lombok.Getter;

public class DefaultCollectionService implements CollectionService
{
    @Inject
    private SourceProviderManager provider;
    private final Set<URI> sources = Sets.newHashSet();


    @Override
    public void register(SourceProvider provider)
    {
        this.provider.addProvider(provider);
    }

    @Override
    public Iterable<SourceContent> collect() throws CollectionException
    {
        return null;
    }

    public void addSource(String uri)
    {}

    public void removeSource(String uri)
    {}
}
