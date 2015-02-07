package me.wener.telletsj.neo;

import java.util.Date;
import java.util.Map;

public interface TypedMap extends Map<String, Object>
{
    Integer getInteger(String key);

    Long getLong(String key);

    Double getDouble(String key);

    Float getFloat(String key);

    Date getDate(String key);


    <T> T get(String key, Class<T> type);
}
