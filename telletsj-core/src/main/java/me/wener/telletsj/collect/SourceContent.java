package me.wener.telletsj.collect;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import java.util.Map;
import lombok.Data;
import lombok.SneakyThrows;

/**
 * 收集到的内容<br>
 * 对于一些特殊的内容,可以考虑实现子类或使用 {@link SourceContent#getProperties()} 来做定制
 */
@Data
public class SourceContent
{
    /**
     * 该源的属性
     */
    private final Map<String, Object> properties = Maps.newConcurrentMap();
    private CollectSource source;
    /**
     * 文件名
     */
    private String filename;
    /**
     * 文章源内容
     */
    private String content;
    /**
     * 内容Hash值
     */
    private String sha;

    /**
     * 填充内容,在有些情况下内容可以考虑延迟获取
     */
    @SneakyThrows
    public final String getContent()
    {
        if (content == null)
            fillContent0();
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
        this.setSha(CollectUtil.sha(getContent()));
    }

    protected void fillContent0() throws CollectionException
    {
    }
}
