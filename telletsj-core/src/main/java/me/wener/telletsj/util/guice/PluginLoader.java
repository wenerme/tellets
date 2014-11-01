package me.wener.telletsj.util.guice;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.Reflection;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.ServiceLoader;
import java.util.Set;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PluginLoader<T>
{
    @Getter
    private final String packageName;
    @Getter
    private final Class<T> pluginType;
    @Getter
    private final ClassLoader loader;
    private final ImmutableSet<Class<T>> plugins;

    public PluginLoader(Class<T> pluginType, String packageName)
    {
        this(pluginType, packageName, ClassLoader.getSystemClassLoader());
    }

    public PluginLoader(Class<T> pluginType, String packageName, ClassLoader loader)
    {
        this.packageName = packageName;
        this.pluginType = pluginType;
        this.loader = loader;
        try
        {
            plugins = ImmutableSet.copyOf(loadByScanPath());
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取一个 {@code pluginType} 类型,包为该类型所在包的插件加载器
     */
    public static <PT> PluginLoader<PT> of(Class<PT> pluginType)
    {
        return of(pluginType, Reflection.getPackageName(pluginType));
    }

    public static <PT> PluginLoader<PT> of(Class<PT> pluginType, String packageName)
    {
        return new PluginLoader<>(pluginType, packageName);
    }

    @SuppressWarnings("unchecked")
    protected Collection<Class<T>> loadByScanPath() throws IOException
    {
        ClassPath classPath;
        Set<Class<T>> classes = Sets.newHashSet();
        classPath = ClassPath.from(pluginType.getClassLoader());

        ImmutableSet<ClassPath.ClassInfo> infos;
        infos = classPath.getTopLevelClassesRecursive(packageName);
        for (ClassPath.ClassInfo info : infos)
        {
            Class<?> clazz = info.load();
            if (pluginType.isAssignableFrom(clazz))
                classes.add((Class<T>) clazz);
        }
        return classes;
    }

    public ImmutableSet<Class<T>> getPlugins()
    {
        return plugins;
    }
}
