package me.wener.telletsj.collect.impl;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import me.wener.telletsj.collect.CollectUtil;

/**
 * 目录数据源
 */
public class DirectoryLookupCollectionSource extends AbstractCollectSource
{
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
    private File root;

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
