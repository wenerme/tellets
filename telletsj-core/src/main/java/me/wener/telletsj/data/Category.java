package me.wener.telletsj.data;

import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


public interface Category extends AliasLabel<Category>
{
    Category getParent();
}
