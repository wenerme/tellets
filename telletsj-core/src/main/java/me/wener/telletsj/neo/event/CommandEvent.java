package me.wener.telletsj.neo.event;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.wener.telletsj.util.event.BaseEvent;

@EqualsAndHashCode(callSuper = false)
@Data
public class CommandEvent extends BaseEvent
{
    private final List<String> args = Lists.newArrayList();
    private String command;

    public CommandEvent(Object source)
    {
        super(source);
    }
}
