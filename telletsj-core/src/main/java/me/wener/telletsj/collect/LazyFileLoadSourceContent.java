package me.wener.telletsj.collect;

import java.io.File;
import java.io.IOException;
import lombok.SneakyThrows;

class LazyFileLoadSourceContent extends SourceContent
{
    private final File file;

    public LazyFileLoadSourceContent(File file)
    {
        this.file = file;
    }

    @Override
    protected void fillContent() throws CollectionException
    {
        try
        {
            setContent(CollectUtil.readString(file));
        } catch (IOException e)
        {
            throw new CollectionException(e);
        }
    }
}
