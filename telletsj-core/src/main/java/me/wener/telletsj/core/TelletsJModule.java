package me.wener.telletsj.core;

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
