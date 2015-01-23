package me.wener.telletsj.neo;

import com.google.common.base.Preconditions;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import me.wener.telletsj.neo.api.TJStore;

public class TJStoreImpl implements TJStore
{
    @Named("metadata-map")
    @Inject
    private Map<String, Map<String, Object>> metadata;
    private Map<String, String> contents;
    private Map<String, String> linkMap;// link to id
    private Map<String, String> sha1Map;// sha1 to id

    @Override
    public Map<String, Object> getMeta(String id)
    {
        return metadata.get(findId(id));
    }

    @Override
    public TJStore setMeta(String id, Map<String, Object> meta)
    {
        Preconditions.checkNotNull(id);
        metadata.put(id, meta);
        return this;
    }

    @Override
    public boolean exists(String id)
    {
        return findId(id) != null;
    }

    @Override
    public String getContent(String id)
    {
        return contents.get(findId(id));
    }

    @Override
    public TJStoreImpl setContent(String id, String content)
    {
        Preconditions.checkNotNull(id);
        this.contents.put(findId(id), content);
        return this;
    }

    private String findId(String id)
    {
        if (metadata.containsKey(id))
        {
            return id;
        }
        return id;
    }

}
