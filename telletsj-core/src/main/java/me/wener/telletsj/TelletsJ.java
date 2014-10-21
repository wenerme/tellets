package me.wener.telletsj;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mycila.guice.ext.closeable.CloseableModule;
import com.mycila.guice.ext.jsr250.Jsr250Module;
import me.wener.telletsj.util.guice.ChainInjector;

public class TelletsJ
{
    public static void main(String[] args)
    {
        new TelletsJ().start();
    }

    public void start()
    {
        Injector injector = ChainInjector
                .none()
                .and(Jsr250Module.class, CloseableModule.class)
                .getInjector();
    }
}
