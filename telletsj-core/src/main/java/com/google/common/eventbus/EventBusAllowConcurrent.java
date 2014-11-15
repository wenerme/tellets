package com.google.common.eventbus;

import static com.google.common.base.Preconditions.*;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

public class EventBusAllowConcurrent extends EventBus
{
    private final Executor executor;

    private final ConcurrentLinkedQueue<EventWithSubscriber> eventsToDispatch =
            new ConcurrentLinkedQueue<EventWithSubscriber>();

    public EventBusAllowConcurrent(Executor executor, SubscriberExceptionHandler subscriberExceptionHandler)
    {
        super(subscriberExceptionHandler);
        this.executor = checkNotNull(executor);
    }

    @Override
    void enqueueEvent(Object event, EventSubscriber subscriber)
    {
        eventsToDispatch.offer(new EventWithSubscriber(event, subscriber));
    }

    /**
     * Dispatch {@code events} in the order they were posted, regardless of
     * the posting thread.
     */
    @SuppressWarnings("deprecation") // only deprecated for external subclasses
    @Override
    protected void dispatchQueuedEvents()
    {
        while (true)
        {
            EventWithSubscriber eventWithSubscriber = eventsToDispatch.poll();
            if (eventWithSubscriber == null)
            {
                break;
            }

            dispatch(eventWithSubscriber.event, eventWithSubscriber.subscriber);
        }
    }

    /**
     * Calls the {@link #executor} to dispatch {@code event} to {@code subscriber}.
     */
    @Override
    void dispatch(final Object event, final EventSubscriber subscriber)
    {
        checkNotNull(event);
        checkNotNull(subscriber);
        if (!subscriber.getMethod().isAnnotationPresent(AllowConcurrentEvents.class))
        {
            super.dispatch(event, subscriber);
        } else
        {
            executor.execute(
                    new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            EventBusAllowConcurrent.super.dispatch(event, subscriber);
                        }
                    });
        }
    }
}
