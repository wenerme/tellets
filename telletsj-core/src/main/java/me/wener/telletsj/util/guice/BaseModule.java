package me.wener.telletsj.util.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

public abstract class BaseModule<SELF extends AbstractModule> extends AbstractModule
{
    @Inject
    private Injector injector;

    @SuppressWarnings("unchecked")
    protected SELF install(Class<? extends Module> clazz)
    {
        install(injector().getInstance(clazz));
        return (SELF) this;
    }

    protected Injector injector()
    {
        return injector;
    }

    protected <T> T instance(Class<T> clazz)
    {
        return injector.getInstance(clazz);
    }
}
