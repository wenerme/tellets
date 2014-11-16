package me.wener.telletsj.util.inject;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import java.util.Collections;
import java.util.List;

/**
 * 链式的配置 Injector 主要使用 and 和 then.
 * 只有使用 then 和 getInjector 时才会触发注入,使用 and 的时候只会添加到预备列表
 */
public class ChainInjector
{
    List<Module> moduleList = Lists.newArrayList();
    private Injector injector;

    public ChainInjector()
    {
    }

    protected ChainInjector(Injector injector)
    {
        this.injector = injector;
    }

    public static ChainInjector none()
    {
        return new ChainInjector();
    }

    public static ChainInjector start(Module... modules)
    {
        return none().and(modules);
    }

    public static ChainInjector start(Injector injector)
    {
        return new ChainInjector(injector);
    }

    public ChainInjector and(Module... modules)
    {
        Collections.addAll(this.moduleList, modules);
        return this;
    }

    @SafeVarargs
    public final ChainInjector and(Class<? extends Module>... modules)
    {
        for (Class<? extends Module> module : modules)
        {
            Module m;

            try
            {
                m = module.newInstance();
            } catch (Exception e)
            {
                throw new RuntimeException(e);
            }

            moduleList.add(m);
        }
        return this;
    }

    public ChainInjector and(Iterable<Module> modules)
    {
        Iterables.addAll(moduleList, modules);
        return this;
    }

    private ChainInjector installBefore()
    {
        if (injector != null)
            for (Module module : moduleList)
            {
                injector.injectMembers(module);
            }

        if (injector == null)
            injector = Guice.createInjector(moduleList);
        else
            injector = injector.createChildInjector(moduleList);

        moduleList.clear();
        return this;
    }

    @SafeVarargs
    public final ChainInjector then(Class<? extends Module>... modules)
    {
        return installBefore().and(modules);
    }

    public ChainInjector then(Iterable<Module> modules)
    {
        return installBefore().and(modules);
    }

    public ChainInjector then(Module... modules)
    {
        return installBefore().and(modules);
    }

    public ChainInjector then(Module module)
    {
        return installBefore().and(module);
    }

    public Injector getInjector()
    {
        installBefore();
        return injector;
    }
}
