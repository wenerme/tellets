package me.wener.telletsj.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import lombok.Getter;

public class TelletsJ
{
    @Getter
    private final TelletsJConfig config;
    private Injector injector;

    public TelletsJ(TelletsJConfig config) {this.config = config;}

    public static void main(String[] args)
    {
        new TelletsJ(null).start();
    }

    public void start()
    {
        injector = Guice.createInjector(Stage.DEVELOPMENT, new TelletsJModule());
    }

}
