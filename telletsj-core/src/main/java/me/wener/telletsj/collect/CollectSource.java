package me.wener.telletsj.collect;

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

    void fillContent(SourceContent source);
}
