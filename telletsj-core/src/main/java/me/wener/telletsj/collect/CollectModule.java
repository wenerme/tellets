/*
 * Created by IntelliJ IDEA.
 * User: Wener
 * Date: 2014/10/27
 * Time: 1:33
 */
package me.wener.telletsj.collect;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.reflect.Reflection;
import javax.inject.Named;
import lombok.SneakyThrows;
import me.wener.telletsj.util.guice.BaseModule;
import me.wener.telletsj.util.guice.ClassFilter;
import me.wener.telletsj.util.guice.PluginLoader;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;

public class CollectModule extends BaseModule<CollectModule>
{
    private static final String PROVIDER_SCAN_PACKAGE = "me.wener.telletsj.collect";

    @SneakyThrows
    protected void configure()
    {
        // 初始化 VFS
        FileSystemManager fsm = VFS.getManager();
        bind(FileSystemManager.class)
                .toInstance(fsm);
        // 初始化 Provider
        SourceProviderManager provider = new SourceProviderManager();
        bind(SourceProvider.class).toInstance(provider);

        // 包扫描所有已经实现的Provider
        ImmutableSet<Class<SourceProvider>> plugins = new PluginLoader<>(SourceProvider.class, Reflection
                .getPackageName(SourceProvider.class), ClassLoader.getSystemClassLoader())
                .getPlugins();

        for (Class<SourceProvider> clazz : Iterables.filter(plugins, ClassFilter.annotatedBy(Named.class)))
        {
            SourceProvider instance = clazz.newInstance();
            requestInjection(instance);
            provider.addProvider(instance);
        }

        // 初始 Service
        DefaultCollectionService service = new DefaultCollectionService();
        bind(CollectionService.class).toInstance(service);
    }
}
