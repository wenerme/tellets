package me.wener.telletsj.process;

import com.google.common.base.CharMatcher;

public class ProcessUtil
{
    private final static CharMatcher LR_CR_MATCHER = CharMatcher.anyOf("\r\n");
    private final static CharMatcher TRIM_MATCHER = CharMatcher.anyOf("\r\n\t ");

    private ProcessUtil() { }

    public static String trim(String content)
    {
        return TRIM_MATCHER.trimFrom(content);
    }

}
