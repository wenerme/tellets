package me.wener.telletsj.data;

import java.util.Collection;
import javax.annotation.Nullable;

public interface DataStore<T extends Identifiable>
{
    void store(T item);

    boolean remove(T item);

    @Nullable
    T find(String id);

    Collection<T> all();

    int size();

}
