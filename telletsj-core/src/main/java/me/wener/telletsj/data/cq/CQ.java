package me.wener.telletsj.data.cq;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.simple.SimpleQuery;

public class CQ
{
    public static <T, A> Query<T> any(Attribute<T, A> attribute)
    {
        return new SimpleQuery<T, A>(attribute)
        {
            @Override
            protected boolean matchesSimpleAttribute(SimpleAttribute<T, A> attribute, T object)
            {
                return true;
            }

            @Override
            protected boolean matchesNonSimpleAttribute(Attribute<T, A> attribute, T object)
            {
                return true;
            }

            @Override
            protected int calcHashCode()
            {
                return 0;
            }
        };
    }
}
