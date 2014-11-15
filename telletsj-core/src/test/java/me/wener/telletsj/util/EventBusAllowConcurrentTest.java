package me.wener.telletsj.util;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import me.wener.telletsj.util.event.Events;
import org.junit.Test;

@Slf4j
public class EventBusAllowConcurrentTest
{

    private volatile long y;
    private volatile long v;
    private volatile long z;
    private volatile long x;

    @Test
    public void test() throws InterruptedException
    {
        EventBus eventBus = Events.createAllowConcurrentWithExceptionDispatch();
        eventBus.register(this);
        eventBus.post("Fine");
        assert z > 0;
        assert v > 0;
    }

    @Subscribe
    @AllowConcurrentEvents
    public void testY(String s) throws InterruptedException
    {
        EventBusAllowConcurrentTest.log.info("GOT Y {}", s);
        Thread.sleep(200);
        y = System.currentTimeMillis();
    }

    @Subscribe
    @AllowConcurrentEvents
    public void testX(String s) throws InterruptedException
    {
        EventBusAllowConcurrentTest.log.info("GOT X {}", s);
        Thread.sleep(200);
        x = System.currentTimeMillis();
    }

    @Subscribe
    public void test(String s) throws InterruptedException
    {
        EventBusAllowConcurrentTest.log.info("GOT Z {}", s);
        Thread.sleep(200);
        z = System.currentTimeMillis();
    }

    @Subscribe
    public void testV(String s) throws InterruptedException
    {
        EventBusAllowConcurrentTest.log.info("GOT V {}", s);
        Thread.sleep(200);
        v = System.currentTimeMillis();
    }

}
