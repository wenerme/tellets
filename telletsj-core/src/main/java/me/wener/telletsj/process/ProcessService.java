package me.wener.telletsj.process;

import com.google.common.base.Supplier;

/**
 * 处理服务接口
 */
public interface ProcessService
{
    ContentInfo tryProcess(String filename, Supplier<String> content) throws ProcessException;

    void register(MetaProcessor processor);
}
