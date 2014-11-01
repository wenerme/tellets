package me.wener.telletsj.process;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true, fluent = true)
public class ContentInfo
{
    @Setter(AccessLevel.PROTECTED)
    private ArrayListMultimap<String, String> sections = ArrayListMultimap.create();
    @Setter(AccessLevel.PROTECTED)
    private Map<String, String> meta = Maps.newHashMap();

    private String restContent;
    private String rawContent;

    public List<String> section(String name)
    {
        return sections.get(name);
    }

    public String sectionOne(String name)
    {
        List<String> sec = sections.get(name);
        return sec.size() == 0 ? null : sec.get(0);
    }

    public String meta(String key)
    {
        return meta.get(key);
    }
}
