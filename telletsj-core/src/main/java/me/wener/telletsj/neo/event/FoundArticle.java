package me.wener.telletsj.neo.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.wener.telletsj.util.event.BaseEvent;

@EqualsAndHashCode(callSuper = true)
@Data
public class FoundArticle extends BaseEvent
{
    private String content;

    public FoundArticle(Object source)
    {
        super(source);
    }
}
