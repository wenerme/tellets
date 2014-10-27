package me.wener.telletsj.collect;

import java.io.File;
import lombok.SneakyThrows;

public class LazyFileLoadSourceContent extends SourceContent
{
    private final File file;

    public LazyFileLoadSourceContent(File file)
    {
        this.file = file;
    }

    @Override
    @SneakyThrows
    protected void fillContent()
    {
        setContent(CollectUtil.readString(file));
    }
}
