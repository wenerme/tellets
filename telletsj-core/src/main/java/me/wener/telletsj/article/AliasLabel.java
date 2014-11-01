package me.wener.telletsj.article;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true, fluent = true)
public class AliasLabel<T>
{
    private String raw;
    private final List<String> aliases = Lists.newArrayList();

    /**
     * @param more 添加的别名
     * @return 本实例
     */
    @SuppressWarnings("unchecked")
    public T alias(String more)
    {
        aliases.add(more);
        return (T)this;
    }
}
