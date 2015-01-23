package me.wener.telletsj.neo;

import com.google.common.base.Preconditions;
import java.util.Map;
import me.wener.telletsj.neo.api.Metadata;

public class MetadataImpl implements Metadata
{
    private final Map<String, Object> data;

    public MetadataImpl(Map<String, Object> data) {this.data = data;}

    @Override
    @SuppressWarnings("unchecked")
    public <T> T metadata(String key)
    {
        return (T) data.get(key);
    }

    @Override
    public MetadataImpl metadata(String key, Object meta)
    {
        if (meta == null)
        {
            data.remove(key);
        }
        data.put(key, meta);
        return this;
    }

    @Override
    public String id()
    {
        String id = link();
        if (id == null)
            id = sha1();
        Preconditions.checkNotNull(id, "无法获取到 ID 信息");
        return id;
    }

    @Override
    public String sha1()
    {
        return metadata("sha1");
    }

    @Override
    public Metadata sha1(String sha1)
    {
        return metadata("sha1", sha1);
    }

    @Override
    public String title()
    {
        return metadata("sha1");
    }

    @Override
    public Metadata title(String title)
    {
        return metadata("title", title);
    }

    @Override
    public String link()
    {
        return metadata("link");
    }

    @Override
    public Metadata link(String link)
    {
        return metadata("link", link);
    }

    @Override
    public Long timestamp()
    {
        return metadata("timestamp");
    }

    @Override
    public Metadata timestamp(Long timestamp)
    {
        return metadata("timestamp", timestamp);
    }

    @Override
    public Map<String, Object> asMap()
    {
        return data;
    }
}
