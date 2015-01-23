package me.wener.telletsj.util.event;

import java.util.EventObject;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
public abstract class BaseEvent extends EventObject
{
    @Getter
    private boolean defaultPrevented = false;

    public BaseEvent(Object source)
    {
        super(source);
    }

    /**
     * @param <T> 所需类型
     * @return 将源转为指定类型
     */
    @SuppressWarnings("unchecked")
    public <T> T asSource()
    {
        return (T) getSource();
    }

    /**
     * 阻止默认动作,根据具体实现来处理
     */
    public void preventDefault()
    {
        defaultPrevented = true;
    }
}
