package me.wener.telletsj.data;

import com.mysema.query.annotations.QueryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@QueryEntity
public class Tag extends AliasLabel<Tag>
{

    public Tag(String raw)
    {
        super(raw);
    }
}
