package me.wener.telletsj.process;

import com.google.common.base.Splitter;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ContentInfo
{
    public static final Splitter COMMA_SPLITTER = Splitter.on(',').omitEmptyStrings().trimResults();
    private final ArrayListMultimap<String, String> sections = ArrayListMultimap.create();
    private final Map<String, String> meta = Maps.newHashMap();
    private String restContent;
    private String rawContent;

    public List<String> sections(String name)
    {
        return sections.get(name);
    }

    public String section(String name)
    {
        List<String> sec = sections.get(name);
        return sec.size() == 0 ? null : sec.get(0);
    }

    public String meta(String key)
    {
        return meta.get(key);
    }

    /**
     * 以List的方式获取Meta值
     *
     * @param metaKey meta 键值
     * @return 以 逗号 分割的list
     */
    public List<String> list(String metaKey)
    {
        return COMMA_SPLITTER.splitToList(meta(metaKey));
    }

    public ContentInfo meta(String key, String val)
    {
        meta.put(key, val);
        return this;
    }
}
