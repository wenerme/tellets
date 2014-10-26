package me.wener.telletsj.collect;

import java.util.Set;

public interface SourceProvider
{
    Set<String> getSchemes();

    CollectSource get(CollectSourceConfig config)
            throws IllegalArgumentException, UnsupportedOperationException;
}
