package me.wener.telletsj.neo;

import java.util.Map;

public class MoreMaps
{
    public static TypedMap typed(Map<String, Object> map)
    {
        if (map instanceof TypedMap)
        {
            return (TypedMap) map;
        }
        return new TypedMapImpl(map);
    }
}
