package me.wener.telletsj.process;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.Getter;

public class MultiProcessException extends ProcessException
{
    @Getter
    protected final List<ProcessException> exceptions = Lists.newArrayList();
    @Override
    public void printStackTrace()
    {
        super.printStackTrace();
        for (ProcessException exception : exceptions)
        {
            System.out.println("WITH EXTRA EXCEPTION: "+exception.getMessage());
        }
    }

}
