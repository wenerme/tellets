package me.wener.telletsj.core;

import lombok.SneakyThrows;
import me.wener.telletsj.util.inject.BaseModule;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;

public class TelletsJModule extends BaseModule<TelletsJModule>
{
    @Override
    @SneakyThrows
    protected void configure()
    {
        // 初始化 VFS
        FileSystemManager fsm = VFS.getManager();
        bind(FileSystemManager.class)
                .toInstance(fsm);
    }
}
