package me.wener.telletsj.util.inject;

import com.google.common.base.Predicate;
import java.lang.annotation.Annotation;
import javax.annotation.Nullable;

public class ClassFilter
{
    private ClassFilter()
    {
    }

    public static Predicate<Class<?>> annotatedBy(final Class<? extends Annotation> type)
    {
        return new Predicate<Class<?>>()
        {
            @Override
            public boolean apply(@Nullable Class<?> input)
            {
                return input != null && input.isAnnotationPresent(type);
            }
        };
    }

    public static Predicate<Class<?>> instanceOf(final Class<?> type)
    {
        return new Predicate<Class<?>>()
        {
            @Override
            public boolean apply(@Nullable Class<?> input)
            {
                return input != null && type.isAssignableFrom(input);
            }
        };
    }

}
