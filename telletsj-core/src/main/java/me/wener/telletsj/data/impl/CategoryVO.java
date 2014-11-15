package me.wener.telletsj.data.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.wener.telletsj.data.Category;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class CategoryVO extends SimpleAliasLabel<Category> implements Category
{
    private Category parent;
}
