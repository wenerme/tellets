package me.wener.telletsj.process;

public interface MetaProcessor
{
    /**
     * @param filename 文件名
     * @return 指定文件名是否可以处理
     */
    boolean canProcess(String filename);

    /**
     * @param content 处理内容
     * @return 处理后取得的内容信息
     * @throws ProcessException 解析中发生异常,有可能只是文件名判断可处理,但实际内容是无法处理的情况
     */
    ContentInfo process(String content) throws ProcessException;
}
