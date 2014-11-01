package me.wener.telletsj.collect.vfs;

import com.google.common.collect.Lists;
import java.util.List;
import me.wener.telletsj.collect.CollectionException;
import me.wener.telletsj.collect.impl.AbstractCollectSource;
import me.wener.telletsj.collect.impl.SourceContent;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileType;

public class FileObjectCollectSource extends AbstractCollectSource
{
    private final FileObject file;

    public FileObjectCollectSource(FileObject file)
    {
        this.file = file;
    }

    private static List<FileObject> getAllChildren(FileObject file, List<FileObject> all) throws FileSystemException
    {
        if (file.getType() == FileType.FILE)
        {
            all.add(file);
        } else
        {
            for (FileObject fileObject : file.getChildren())
            {
                getAllChildren(fileObject, all);
            }
        }
        return all;
    }

    @Override
    public Iterable<SourceContent> collect() throws CollectionException
    {
        try
        {
            if (file.getType() == FileType.FILE)
            {
                return Lists.<SourceContent>newArrayList(new FileObjectSourceContent(file));
            }

            List<FileObject> files = getAllChildren(file, Lists.<FileObject>newArrayList());
            List<SourceContent> contents = Lists.newArrayList();
            for (FileObject fileObject : file.getChildren())
            {
                contents.add(new FileObjectSourceContent(fileObject));
            }
            return contents;
        } catch (FileSystemException e)
        {
            throw new CollectionException(e);
        }
    }
}
