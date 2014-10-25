package me.wener.telletsj.collect.vfs;

import com.google.common.base.Preconditions;
import lombok.SneakyThrows;
import me.wener.telletsj.collect.CollectUtil;
import me.wener.telletsj.collect.SourceContent;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileType;

public class FileObjectSourceContent extends SourceContent
{
    private final FileObject file;

    public FileObjectSourceContent(FileObject file) throws FileSystemException
    {
        this.file = file;
        Preconditions.checkArgument(file.getType() == FileType.FILE, "Must be file object");
        setFilename(file.getName().getBaseName());
    }

    @Override
    @SneakyThrows
    protected void fillContent0()
    {
        String content = CollectUtil.readString(file.getContent().getInputStream());
        setContent(content);
    }
}
