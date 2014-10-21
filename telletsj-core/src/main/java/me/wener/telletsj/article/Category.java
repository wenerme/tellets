package me.wener.telletsj.article;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Category
{
    private String name;
}
