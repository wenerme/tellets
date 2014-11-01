package me.wener.telletsj.process;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import javax.inject.Named;
import me.wener.telletsj.util.guice.BaseModule;
import me.wener.telletsj.util.guice.ClassFilter;
import me.wener.telletsj.util.guice.PluginLoader;

public class ProcessModule extends BaseModule<ProcessModule>
{
    @Override
    protected void configure()
    {
        DefaultProcessService service = injector().getInstance(DefaultProcessService.class);
        bind(ProcessService.class)
                .toInstance(service);
        // 包扫描所有已经实现的Provider
        ImmutableSet<Class<MetaProcessor>> plugins = PluginLoader.of(MetaProcessor.class).getPlugins();

        for (Class<MetaProcessor> clazz : Iterables.filter(plugins, ClassFilter.annotatedBy(Named.class)))
        {
            service.register(instance(clazz));
        }
    }
}
