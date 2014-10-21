package me.wener.telletsj.core;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Tag
{
    private String name;
}
