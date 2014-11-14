package me.wener.telletsj.data;

import java.util.Set;

public interface AliasLabel<T>
{
    T alias(String st);

    Set<String> aliases();

    String getName();

    T setName(String name);
}
