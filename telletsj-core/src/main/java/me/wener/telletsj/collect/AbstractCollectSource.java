package me.wener.telletsj.collect;

import com.google.common.base.Preconditions;
import lombok.Getter;

public abstract class AbstractCollectSource implements CollectSource
{
    public abstract Iterable<SourceContent> collect() throws CollectionException;

    @Override
    public boolean isChanged()
    {
        return true;
    }

}
