package me.wener.telletsj.util.event;

import com.google.common.eventbus.SubscriberExceptionContext;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ExceptionEvent
{
    private final Throwable exception;
    private final SubscriberExceptionContext context;
    private final Object extra;

    public ExceptionEvent(Throwable exception)
    {
        this(exception, null);
    }

    public ExceptionEvent(Throwable exception, Object extra)
    {
        this(exception, null, extra);
    }

    public ExceptionEvent(Throwable exception, SubscriberExceptionContext context)
    {
        this(exception, context, null);
    }

    public ExceptionEvent(Throwable exception, SubscriberExceptionContext context, Object extra)
    {
        this.exception = exception;
        this.context = context;
        this.extra = extra;
    }
}
