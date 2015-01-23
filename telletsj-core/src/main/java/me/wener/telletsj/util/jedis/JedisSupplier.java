package me.wener.telletsj.util.jedis;

import com.google.common.base.Supplier;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisSupplier implements Supplier<Jedis>
{
    private final JedisPool pool;

    public JedisSupplier(JedisPool pool) {this.pool = pool;}

    @Override
    public Jedis get()
    {
        return pool.getResource();
    }
}
