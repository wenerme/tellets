package me.wener.telletsj.collect;

import java.io.File;
import java.io.IOException;
import me.wener.telletsj.collect.impl.SourceContent;
import me.wener.telletsj.util.IO;

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
            setContent(IO.readString(file));
        } catch (IOException e)
        {
            throw new CollectionException(e);
        }
    }
}
