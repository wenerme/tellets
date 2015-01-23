package me.wener.telletsj.util.jedis;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import java.util.AbstractMap;
import java.util.Set;
import redis.clients.jedis.Jedis;

public class StringMap extends AbstractMap<String, String>
{
    private final Supplier<Jedis> supplier;
    private final String mapKey;

    public StringMap(Supplier<Jedis> supplier, String mapKey)
    {
        this.supplier = supplier;
        this.mapKey = mapKey;
    }

    @Override
    public Set<Entry<String, String>> entrySet()
    {
        try (Jedis jedis = supplier.get())
        {
            return ImmutableMap.copyOf(jedis.hgetAll(mapKey)).entrySet();
        }
    }

    @Override
    public boolean containsKey(Object key)
    {
        try (Jedis jedis = supplier.get())
        {
            return jedis.hexists(mapKey, String.valueOf(key));
        }
    }

    @Override
    public String put(String key, String value)
    {
        try (Jedis jedis = supplier.get())
        {
            String old = get(key);
            jedis.hset(mapKey, key, value);
            return old;
        }
    }

    @Override
    public String get(Object key)
    {
        try (Jedis jedis = supplier.get())
        {
            return jedis.hget(mapKey, String.valueOf(key));
        }
    }

    @Override
    public int size()
    {
        try (Jedis jedis = supplier.get())
        {
            return jedis.hlen(mapKey).intValue();
        }
    }

    @Override
    public Set<String> keySet()
    {
        try (Jedis jedis = supplier.get())
        {
            return jedis.hkeys(mapKey);
        }
    }
}
