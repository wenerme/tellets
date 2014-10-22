package me.wener.telletsj.util.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import lombok.SneakyThrows;

public abstract class BaseModule<SELF extends AbstractModule> extends AbstractModule
{
    @SuppressWarnings("unchecked")
    @SneakyThrows
    protected SELF install(Class<? extends Module> clazz)
    {
        install(clazz.newInstance());
        return (SELF)this;
    }
}
