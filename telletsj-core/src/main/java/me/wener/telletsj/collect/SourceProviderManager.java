package me.wener.telletsj.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.concurrent.Immutable;
import javax.inject.Singleton;
import lombok.Getter;

@Singleton
public class SourceProviderManager implements SourceProvider
{
    private final List<SourceProvider> providers = Lists.newArrayList();
    private final Map<String, SourceProvider> schemeMap = Maps.newHashMap();

    public SourceProviderManager()
    { }

    public void addProvider(SourceProvider provider)
    {
        providers.add(provider);
        Preconditions.checkArgument(Collections
                .disjoint(schemeMap.keySet(), provider.getSchemes()), "Some scheme already registered");
        for (String scheme : provider.getSchemes())
        {
            schemeMap.put(scheme, provider);
        }
    }

    @Override
    public Set<String> getSchemes()
    {
        return schemeMap.keySet();
    }


    @Override
    public CollectSource get(URI uri) throws IllegalArgumentException, UnsupportedOperationException
    {
        SourceProvider provider = schemeMap.get(uri.getScheme());
        Preconditions.checkArgument(provider != null,"URI scheme not supported: "+uri);
        //noinspection ConstantConditions
        return provider.get(uri);
    }

    public List<SourceProvider> getProviders()
    {
        return ImmutableList.copyOf(providers);
    }
}
