package me.wener.telletsj.data;

public interface Category extends AliasLabel<Category>
{
    Category getParent();

    Category setParent(Category parent);
}
