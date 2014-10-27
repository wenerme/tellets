package me.wener.telletsj.process;

public interface MetaProcessor
{
    MetaData process(String content) throws ProcessException;
}
