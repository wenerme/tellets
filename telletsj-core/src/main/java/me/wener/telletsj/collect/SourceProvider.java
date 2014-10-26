package me.wener.telletsj.collect;

import java.util.List;
import java.util.Set;
import javax.inject.Provider;

public interface SourceProvider
{
    Set<String> getSchemes();

    CollectSource get(CollectSourceConfig config)
            throws IllegalArgumentException, UnsupportedOperationException;
}
