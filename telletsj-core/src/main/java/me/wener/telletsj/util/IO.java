package me.wener.telletsj.util;

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

@SuppressWarnings("unused")
public class IO
{
    private static final HashFunction SHA_FUNCTION = Hashing.sha1();
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private IO()
    {
    }

    public static String sha(String content)
    {
        return SHA_FUNCTION.hashString(content, DEFAULT_CHARSET).toString();
    }

    public static String sha(File file) throws IOException
    {
        return com.google.common.io.Files.hash(file, SHA_FUNCTION).toString();
    }

    public static String toString(File file) throws IOException
    {
        return new String(Files.readAllBytes(file.toPath()), DEFAULT_CHARSET);
    }

    public static String toString(InputStream inputStream) throws IOException
    {
        return CharStreams.toString(new InputStreamReader(inputStream, DEFAULT_CHARSET));
    }
}
