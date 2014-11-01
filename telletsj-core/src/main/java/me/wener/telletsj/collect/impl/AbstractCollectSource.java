package me.wener.telletsj.collect.impl;

import me.wener.telletsj.collect.CollectSource;
import me.wener.telletsj.collect.CollectionException;

public abstract class AbstractCollectSource implements CollectSource
{
    public abstract Iterable<SourceContent> collect() throws CollectionException;

    @Override
    public boolean isChanged()
    {
        return true;
    }

}
