package me.wener.telletsj.collect;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import java.util.Map;
import lombok.Data;
import me.wener.telletsj.collect.CollectSource;

/**
 * 收集到的内容<br>
 * 对于一些特殊的内容,可以考虑实现子类或使用 {@link SourceContent#getProperties()} 来做定制
 */
@Data
public class SourceContent
{
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
    private String hash;

    /**
     * 该源的属性
     */
    private final Map<String,Object> properties = Maps.newConcurrentMap();

    /**
     * 填充内容,在有些情况下内容可以考虑延迟获取
     */
    public final void fillContent() throws CollectionException
    {
        if (content == null)
            fillContent0();
    }
    public final String getHash()
    {
        // 延迟 hash
        if (hash == null)
        {
            try
            {
                getHash0();
            } catch (CollectionException e)
            {
                Throwables.propagate(e);
            }
        }

        return hash;
    }

    protected void getHash0() throws CollectionException
    {
        fillContent();
        setHash(CollectUtil.hash(getContent()));
    }

    protected void fillContent0() throws CollectionException
    {
    }
}
