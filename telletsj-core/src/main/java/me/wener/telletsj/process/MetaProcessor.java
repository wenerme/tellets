package me.wener.telletsj.process;

public interface MetaProcessor
{
    ContentInfo process(String content) throws ProcessException;
}
