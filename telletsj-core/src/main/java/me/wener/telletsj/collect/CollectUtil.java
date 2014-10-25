package me.wener.telletsj.collect;

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
    public static final HashFunction HASH_FUNCTION = Hashing.sha1();
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public static SourceContent fileToSourceContent(File file) throws IllegalArgumentException
    {
        Preconditions.checkArgument(Files.isDirectory(file.toPath()), "File is directory " + file);
        Preconditions.checkArgument(Files.isReadable(file.toPath()), "File unreadable " + file);
        SourceContent content = new LazyFileLoadSourceContent(file);
        content.setFilename(file.getName());
        return content;
    }

    public static String hash(String content)
    {
        return HASH_FUNCTION.hashString(content, DEFAULT_CHARSET).toString();
    }

    public static String hash(File file) throws IOException
    {
        return com.google.common.io.Files.hash(file, HASH_FUNCTION).toString();
    }


    public static String readString(File file) throws IOException
    {
        return new String(Files.readAllBytes(file.toPath()), DEFAULT_CHARSET);
    }

    public static String readString(InputStream inputStream) throws IOException
    {
        return CharStreams.toString(new InputStreamReader(inputStream, DEFAULT_CHARSET));
    }
}
