package me.wener.telletsj.util;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings("unused")
public class SysUtils
{
    public static String getCurrentDirectory()
    {
        return System.getProperty("user.dir");
    }

    public static String tryGetResourceAsString(String path)
    {
        return tryGetResourceAsString(path, Charsets.UTF_8);
    }

    public static String tryGetResourceAsString(String path, Charset charset)
    {
        String string = null;
        InputStream is = SysUtils.tryGetResource(path);
        if (is != null)
        {
            try
            {
                string = CharStreams.toString(new InputStreamReader(is, charset));
            } catch (IOException e)
            {
                log.error("加载属性文件 " + path + " 出现异常", e);
            } finally
            {
                Closeables.closeQuietly(is);
            }
        }
        return string;
    }

    public static InputStream tryGetResource(String path)
    {
        InputStream is = SysUtils.class.getClassLoader().getResourceAsStream(path);
        log.debug("加载文件 {} {}", path, is == null ? "文件未找到!" : "发现文件.");

        if (is == null)
        {
            try
            {
                File file = new File(path);
                if (file.exists() || (file = file.getAbsoluteFile()).exists())
                {
                    log.debug("在目录中发现文件 {}, 尝试加载", file.getPath());
                    try
                    {
                        is = new FileInputStream(file);
                    } catch (FileNotFoundException ignored)
                    {
                        // 已经判断文件存在
                        log.error("加载文件异常", ignored);
                    }
                }
            } catch (SecurityException ex)
            {
                log.warn("当前环境无法操作本地文件, 加载 {} 失败", path);
            }
        }
        return is;
    }

    public static String systemInfo()
    {
        Runtime runtime = Runtime.getRuntime();
        StringBuilder builder = new StringBuilder();
        long maxMemory = runtime.maxMemory();

        builder.append("====== ReportSystemInfo ======\n")
                /* Total number of processors or cores available to the JVM */
                .append("Available processors (cores): ")
                .append(runtime.availableProcessors())
                .append('\n')
                /* This will return Long.MAX_VALUE if there is no preset limit */
                .append("Maximum memory: ")
                .append(maxMemory == Long.MAX_VALUE ? "no limit" : reportMemory(maxMemory))
                .append('\n')
                /* Total amount of free memory available to the JVM */
                .append("Free memory: ")
                .append(reportMemory(runtime.freeMemory()))
                .append('\n')
                /* Total memory currently available to the JVM */
                .append("Total memory available to JVM: ")
                .append(reportMemory(runtime.totalMemory()))
                .append('\n')
                .append("Used memory: ")
                .append(reportMemory(runtime.totalMemory() - runtime.freeMemory()))
                .append('\n')
                .append("Memory usage: ")
                .append((1 - runtime.freeMemory() / (double) runtime.totalMemory()) * 100)
                .append(" %")
                .append('\n')
        ;

        return builder.toString();
    }

    private static String reportMemory(double val)
    {
        val /= 1024;
        return String.format(" %.4f KB | %.4f MB | %.4f GB", val, val / 1024, val / 1024 / 1024);
    }
}
