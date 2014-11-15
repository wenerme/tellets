package me.wener.telletsj.data.cq;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.simple.SimpleQuery;
import java.util.Collection;
import java.util.Collections;

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

    public static <O, A> Query<O> disjoint(final Attribute<O, ? extends Collection<A>> attribute, final Collection<A> attributeValues)
    {
        return new Query<O>()
        {
            @Override
            public boolean matches(O object)
            {
                return Collections.disjoint(attribute.getValues(object), attributeValues);
            }
        };
    }

    public static <O, A> Query<O> nonDisjoint(final Attribute<O, ? extends Collection<A>> attribute, final Collection<A> attributeValues)
    {
        return new Query<O>()
        {
            @Override
            public boolean matches(O object)
            {
                return !Collections.disjoint(attribute.getValues(object), attributeValues);
            }
        };
    }

    public static <O, A> SimpleQuery<O, A> isNull(final Attribute<O, A> attribute)
    {
        return new IsNullQuery<>(attribute);
    }

    static class IsNullQuery<O, A> extends SimpleQuery<O, A>
    {
        /**
         * Creates a new {@link com.googlecode.cqengine.query.simple.SimpleQuery} initialized to make assertions on values of the specified attribute
         *
         * @param attribute The attribute on which the assertion is to be made
         */
        public IsNullQuery(Attribute<O, A> attribute)
        {
            super(attribute);
        }

        @Override
        protected boolean matchesSimpleAttribute(SimpleAttribute<O, A> attribute, O object)
        {
            return attribute.getValue(object) == null;
        }

        @Override
        protected boolean matchesNonSimpleAttribute(Attribute<O, A> attribute, O object)
        {
            return attribute.getValues(object) == null;
        }

        @Override
        protected int calcHashCode()
        {
            return attribute.hashCode();
        }
    }
}
