package me.wener.telletsj.neo;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MapDelegater<K, V> implements Map<K, V>
{
    protected Map<K, V> internal;

    public MapDelegater(Map<K, V> internal)
    {
        this.internal = internal;
    }

    @Override
    public int size() {return internal.size();}

    @Override
    public boolean isEmpty() {return internal.isEmpty();}

    @Override
    public boolean containsKey(Object key) {return internal.containsKey(key);}

    @Override
    public boolean containsValue(Object value) {return internal.containsValue(value);}

    @Override
    public V get(Object key) {return internal.get(key);}

    @Override
    public V put(K key, V value) {return internal.put(key, value);}

    @Override
    public V remove(Object key) {return internal.remove(key);}

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {internal.putAll(m);}

    @Override
    public void clear() {internal.clear();}

    @Override
    public Set<K> keySet() {return internal.keySet();}

    @Override
    public Collection<V> values() {return internal.values();}

    @Override
    public Set<Entry<K, V>> entrySet() {return internal.entrySet();}

    @Override
    public boolean equals(Object o) {return internal.equals(o);}

    @Override
    public int hashCode() {return internal.hashCode();}
}
