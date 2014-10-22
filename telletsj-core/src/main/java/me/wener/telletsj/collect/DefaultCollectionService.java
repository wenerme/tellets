package me.wener.telletsj.collect;

import com.google.common.base.Preconditions;
import lombok.Getter;

public class DefaultCollectionService implements CollectionService
{

    @Getter
    private CollectionServiceConfig config;

    public void setConfig(CollectionServiceConfig config)
    {
        Preconditions.checkState(config == null, "Config already set");
        this.config = config;
    }
}
