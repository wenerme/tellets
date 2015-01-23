package me.wener.telletsj.neo.event;

import lombok.Data;
import me.wener.telletsj.util.event.BaseEvent;

@Data
public class CommandEvent extends BaseEvent
{
    private String command;

    public CommandEvent(Object source)
    {
        super(source);
    }
}
