package me.wener.telletsj;

import java.io.InputStream;
import lombok.SneakyThrows;
import me.wener.telletsj.util.IO;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.junit.BeforeClass;

public class BaseTest
{
    private final FileSystemManager fsm = getManager();

    @BeforeClass
    public static void ensureAssert()
    {
        try
        {
            assert false;
            throw new Error("Assert is disabled");
        } catch (AssertionError ignored) {}
    }

    @SneakyThrows
    protected FileSystemManager getManager()
    {
        return VFS.getManager();
    }

    @SneakyThrows
    public String getContent(String file)
    {
        return readString(fsm.resolveFile(file).getContent().getInputStream());
    }

    @SneakyThrows
    public String readString(InputStream is)
    {
        return IO.readString(is);
    }
}
