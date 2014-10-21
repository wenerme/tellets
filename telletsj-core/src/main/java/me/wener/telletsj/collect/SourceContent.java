package me.wener.telletsj.collect;

import lombok.Data;
import me.wener.telletsj.collect.CollectSource;

/**
 * 收集到的内容
 */
@Data
public class SourceContent
{
    private CollectSource source;
    /**
     * 文件名
     */
    private String filename;
    /**
     * 文章源内容
     */
    private String content;
}
