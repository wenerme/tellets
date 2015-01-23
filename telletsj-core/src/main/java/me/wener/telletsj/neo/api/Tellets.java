package me.wener.telletsj.neo.api;

import com.google.common.eventbus.EventBus;

public interface Tellets
{
    TJStore getStore();

    TJGather getGather();

    TJProcessor getProcessor();

    EventBus getEventBus();
}
