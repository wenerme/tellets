package me.wener.telletsj.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DefaultSourceProvider implements SourceProvider
{
    private final List<SourceProvider> providers = Lists.newArrayList();
    private final Map<String, SourceProvider> schemeMap = Maps.newHashMap();

    public DefaultSourceProvider()
    {
    }

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
    public CollectSource get(CollectSourceConfig config) throws IllegalArgumentException, UnsupportedOperationException
    {
        return null;
    }
}
