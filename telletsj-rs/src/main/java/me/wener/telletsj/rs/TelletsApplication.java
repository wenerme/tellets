package me.wener.telletsj.rs;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.Application;
import me.wener.telletsj.rs.resources.HiApp;

public class TelletsApplication extends Application
{
    private final Map<String, Object> properties = Maps.newConcurrentMap();

    @Override
    public Set<Class<?>> getClasses()
    {
        Set<Class<?>> classes = Sets.newHashSet();
        classes.add(HiApp.class);
        return classes;
    }

    @Override
    public Map<String, Object> getProperties()
    {
        return properties;
    }
}
