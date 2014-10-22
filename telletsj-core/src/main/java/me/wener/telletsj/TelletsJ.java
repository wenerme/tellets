package me.wener.telletsj;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.mycila.guice.ext.closeable.CloseableModule;
import com.mycila.guice.ext.jsr250.Jsr250Module;
import lombok.Getter;
import me.wener.telletsj.util.guice.ChainInjector;

public class TelletsJ
{
    @Getter
    private final TelletsJConfig config;

    public TelletsJ(TelletsJConfig config) {this.config = config;}

    public static void main(String[] args)
    {
        new TelletsJ(null).start();
    }

    public void start()
    {
        Injector injector = Guice.createInjector(Stage.DEVELOPMENT, new TelletsJModule());
    }
}
