package me.wener.telletsj.util.event;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.EventBusAllowConcurrent;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

public class Events
{
    public static EventBus createWithExceptionDispatch()
    {
        final EventBus bus;

        MySubscriberExceptionHandler exceptionHandler = new MySubscriberExceptionHandler();
        bus = new EventBus(exceptionHandler);
        exceptionHandler.setBus(bus);
        return bus;
    }

    public static EventBus createAsyncWithExceptionDispatch()
    {
        return createAsyncWithExceptionDispatch(Executors.newCachedThreadPool());
    }

    public static EventBus createAllowConcurrentWithExceptionDispatch()
    {
        return createAllowConcurrentWithExceptionDispatch(Executors.newCachedThreadPool());
    }

    public static EventBus createAllowConcurrentWithExceptionDispatch(ExecutorService executor)
    {
        final EventBus bus;

        MySubscriberExceptionHandler exceptionHandler = new MySubscriberExceptionHandler();
        bus = new EventBusAllowConcurrent(executor, exceptionHandler);
        exceptionHandler.setBus(bus);
        return bus;
    }

    public static EventBus createAsyncWithExceptionDispatch(ExecutorService executor)
    {
        final EventBus bus;

        MySubscriberExceptionHandler exceptionHandler = new MySubscriberExceptionHandler();
        bus = new AsyncEventBus(executor, exceptionHandler);
        exceptionHandler.setBus(bus);
        return bus;
    }

    @Slf4j
    private static class MySubscriberExceptionHandler implements SubscriberExceptionHandler
    {
        @Setter
        EventBus bus;

        @Override
        public void handleException(Throwable exception, SubscriberExceptionContext context)
        {
            if (context.getEvent() instanceof ExceptionEvent)
            {
                log.error("处理错误过程中发生错误 上下文:" + context, exception);
                return;
            }
            ExceptionEvent event = new ExceptionEvent(exception, context);
            bus.post(event);
        }
    }
}
