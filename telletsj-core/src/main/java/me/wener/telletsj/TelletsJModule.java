package me.wener.telletsj;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.mycila.guice.ext.closeable.CloseableModule;
import com.mycila.guice.ext.jsr250.Jsr250Module;
import me.wener.telletsj.util.guice.BaseModule;

public class TelletsJModule extends BaseModule<TelletsJModule>
{
    @Override
    protected void configure()
    {
        install(CloseableModule.class).install(Jsr250Module.class);
    }
}
