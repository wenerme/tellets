package me.wener.telletsj.collect;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 * 目录数据源
 */
public class DirectoryLookupCollectionSource extends AbstractCollectSource
{
    private File root;
    private static final Predicate<File> IGNORE_HIDDEN_FILE = new Predicate<File>()
    {
        @Override
        public boolean apply(File file)
        {
            return file.getName().startsWith(".");
        }
    };
    private static final Function<File, SourceContent> FILE_TO_SOURCE_CONTENT = new Function<File, SourceContent>()
    {
        @Override
        public SourceContent apply(File file)
        {
            return CollectUtil.fileToSourceContent(file);
        }
    };

    public DirectoryLookupCollectionSource(File root) throws IOException
    {
        this.root = root;

    }

    @Override
    public Iterable<SourceContent> collect()
    {
        return Files.fileTreeTraverser()
                    .preOrderTraversal(root)
                    .filter(IGNORE_HIDDEN_FILE)
                    .transform(FILE_TO_SOURCE_CONTENT);
    }

    @Override
    public boolean isChanged()
    {
        return false;
    }
}
