package me.wener.telletsj.collect;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import java.io.File;
import java.nio.file.Files;
import me.wener.telletsj.collect.impl.SourceContent;

/**
 * 搜集数据时的辅助操作类
 */
@SuppressWarnings("unused")
public class CollectUtil
{

    private static final CharMatcher SLASH_MATCHER = CharMatcher.anyOf("\\/");

    public static SourceContent fileToSourceContent(File file) throws IllegalArgumentException
    {
        Preconditions.checkArgument(!Files.isDirectory(file.toPath()), "File is directory " + file);
        Preconditions.checkArgument(Files.isReadable(file.toPath()), "File unreadable " + file);
        SourceContent content = new LazyFileLoadSourceContent(file);
        content.setFilename(file.getName());
        return content;
    }

    private static String trimLeadingSlash(String str)
    {
        return SLASH_MATCHER.trimLeadingFrom(str);
    }
}
