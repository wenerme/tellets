package me.wener.telletsj.collect;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.CharStreams;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

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
