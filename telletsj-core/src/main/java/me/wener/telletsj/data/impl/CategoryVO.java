package me.wener.telletsj.data.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.wener.telletsj.data.Category;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryVO extends SimpleAliasLabel<Category> implements Category
{
    private Category parent;
}
