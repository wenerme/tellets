package me.wener.telletsj.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Article
{
    private final List<Tag> tags = Lists.newArrayList();
    private final List<Category> categories = Lists.newArrayList();
    private final List<String> features = Lists.newArrayList();
    private final Map<String, String> meta = Maps.newHashMap();
    private String title;
    private String link;
    private Date date;
    private String state;
}
