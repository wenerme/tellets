package me.wener.telletsj.collect;

import me.wener.telletsj.collect.impl.SourceContent;

/**
 * 收集数据源
 * 代表一个文章数据源收集点,例如 GitHub, Git, FTP
 */
public interface CollectSource
{
    /**
     * 获取所有源内容
     */
    Iterable<SourceContent> collect() throws CollectionException;

    /**
     * 是否该收集源有所变化,如果没有,则收集时会跳过该收集源
     */
    boolean isChanged();
}
