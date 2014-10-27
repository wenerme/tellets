package me.wener.telletsj.process;

import com.google.common.base.CharMatcher;

public class ProcessUtil
{
    public final static CharMatcher LR_CR_MATCHER = CharMatcher.anyOf("\r\n");

    private ProcessUtil() { }
}
