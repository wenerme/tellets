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
    public String getHash()
    {
        // 延迟 hash
        if (super.getHash() == null)
        {
            fillContent();
            setHash(CollectUtil.hash(getContent()));
        }

        return super.getHash();
    }

    @Override
    @SneakyThrows
    public void fillContent()
    {
        // 延迟内容
        if (getContent() == null)
            setContent(CollectUtil.readString(file));
    }
}
