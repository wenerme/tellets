package me.wener.telletsj.collect.vfs;


import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import java.util.List;
import java.util.Set;
import javax.inject.Provider;
import me.wener.telletsj.collect.CollectSource;
import me.wener.telletsj.collect.CollectSourceConfig;
import me.wener.telletsj.collect.SourceProvider;
import org.apache.commons.vfs2.FileSystemManager;

public class VFSProvider implements SourceProvider
{
    private final static Set<String> SCHEMES = ImmutableSet.of("jar","zip","res","https","http","tar");

    @Inject
    private FileSystemManager fsm;

    @Override
    public Set<String> getSchemes()
    {
        return SCHEMES;
    }

    @Override
    public CollectSource get(CollectSourceConfig config) throws IllegalArgumentException, UnsupportedOperationException
    {
        return null;
    }

}
