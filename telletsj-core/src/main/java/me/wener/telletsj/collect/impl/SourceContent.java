package me.wener.telletsj.collect.impl;

import com.google.common.base.Throwables;
import java.net.URI;
import lombok.Data;
import me.wener.telletsj.collect.CollectionException;
import me.wener.telletsj.util.IO;

/**
 * 收集到的内容<br>
 * 对于一些特殊的内容,可以考虑实现子类来做定制
 */
@Data
public class SourceContent
{
    /**
     * 该源的属性
     */
//    private final Map<String, Object> properties = Maps.newConcurrentMap();

    private URI uri;
    /**
     * 文件名
     */
    private String filename;
    /**
     * 源内容
     */
    private String content;
    /**
     * 内容Sha1值
     */
    private String sha;

    public final String getContent()
    {
        if (content == null)
            try
            {
                fillContent();
            } catch (CollectionException e)
            {
                Throwables.propagate(e);
            }
        return content;
    }

    public final String getSha()
    {
        // 延迟 hash
        if (sha == null)
        {
            try
            {
                getSha0();
            } catch (CollectionException e)
            {
                Throwables.propagate(e);
            }
        }

        return sha;
    }

    protected void getSha0() throws CollectionException
    {
        this.setSha(IO.sha(getContent()));
    }

    protected void fillContent() throws CollectionException
    {
    }
}
