package me.wener.telletsj.neo;

import com.google.inject.Inject;
import javax.inject.Named;
import redis.clients.jedis.JedisPool;

public class RedisProvider
{
    @Inject
    @Named("jedi-pool")
    private JedisPool pool;

    public void init()
    {

    }
}
