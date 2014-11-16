package me.wener.telletsj.util;

import static me.wener.telletsj.util.strtotime.strtotime;

import org.junit.Test;

public class StrToTimeTest
{
    @Test
    public void test()
    {
        long time = strtotime("2014-1-2").getTime();

        assert strtotime("2014/1/2").getTime() == time;
    }

}
