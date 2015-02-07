package me.wener.telletsj.neo;

import static jodd.typeconverter.TypeConverterManager.convertType;

import com.google.common.collect.Lists;
import java.util.Date;
import java.util.Map;

public class TypedMapImpl extends MapDelegater<String, Object> implements TypedMap
{

    TypedMapImpl(Map<String, Object> map)
    {
        super(map);
    }

    public static void main(String[] args)
    {
        System.out.println(convertType(new Date(), String.class));
        String list = convertType(Lists.newArrayList("a", "b", "c ,d e f"), String.class);
        System.out.println(list);
//        Collection<String> x = TypeConverterManager.convertToCollection(list, List.class, String.class);
//        System.out.println(x);
    }

    @Override
    public Integer getInteger(String key)
    {
        return get(key, Integer.class);
    }

    @Override
    public Long getLong(String key)
    {
        return get(key, Long.class);
    }

    @Override
    public Double getDouble(String key)
    {
        return get(key, Double.class);
    }

    @Override
    public Float getFloat(String key)
    {
        return get(key, Float.class);
    }

    @Override
    public Date getDate(String key)
    {
        return get(key, Date.class);
    }

    @Override
    public <T> T get(String key, Class<T> type)
    {
        return convertType(get(key), type);
    }
}
