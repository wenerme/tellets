package me.wener.telletsj.collect.github;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import me.wener.telletsj.collect.CollectSource;
import me.wener.telletsj.collect.CollectSourceConfig;
import me.wener.telletsj.collect.SourceProvider;

public class GitHubProvider implements SourceProvider
{
    private final static Set<String> SCHEMES = ImmutableSet.of("github");
    @Override
    public Set<String> getSchemes()
    {
        return SCHEMES;
    }

    @Override
    public CollectSource get(CollectSourceConfig config) throws IllegalArgumentException, UnsupportedOperationException
    {
        return null;
    }
}
