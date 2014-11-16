package me.wener.telletsj.collect.vfs;


import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import java.net.URI;
import java.util.Set;
import me.wener.telletsj.collect.CollectSource;
import me.wener.telletsj.collect.SourceProvider;
import me.wener.telletsj.util.inject.Enabled;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;

/**
 * 支持VFS文件系统支持的URI
 */
@Enabled
public class VFSProvider implements SourceProvider
{
    private final static Set<String> SCHEMES = ImmutableSet
            .of("file", "jar", "zip", "res", "http", "tar", "ftp");

    @Inject
    private FileSystemManager fsm;

    @Override
    public Set<String> getSchemes()
    {
        return SCHEMES;
    }

    @Override
    public CollectSource getSource(URI uri) throws IllegalArgumentException, UnsupportedOperationException
    {
        try
        {
            return new FileObjectCollectSource(fsm.resolveFile(uri.toString()));
        } catch (FileSystemException e)
        {
            throw new UnsupportedOperationException(e);
        }
    }
}
