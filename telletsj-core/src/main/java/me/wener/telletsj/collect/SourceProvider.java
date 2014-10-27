package me.wener.telletsj.collect;

import java.net.URI;
import java.util.Set;

public interface SourceProvider
{
    Set<String> getSchemes();

    CollectSource get(URI uri) throws IllegalArgumentException, UnsupportedOperationException;
}
