package me.wener.telletsj.collect.github;

import java.io.IOException;
import me.wener.telletsj.collect.CollectUtil;
import org.apache.commons.vfs2.FileSystemManager;

public class VFSFetcher implements Fetcher
{
    private final FileSystemManager fsm;

    public VFSFetcher(FileSystemManager fsm) {this.fsm = fsm;}

    @Override
    public String fetch(String url) throws IOException
    {
        return CollectUtil.readString(fsm.resolveFile(url).getContent().getInputStream());
    }
}
