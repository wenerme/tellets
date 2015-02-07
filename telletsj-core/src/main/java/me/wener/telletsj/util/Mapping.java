package me.wener.telletsj.util;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.google.common.reflect.AbstractInvocationHandler;
import com.google.common.reflect.Reflection;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import javax.annotation.Nullable;

/**
 * 将属性文件映射为接口
 *
 * @author <a href=mailto:wener@wener.me>wener</a>
 */
@SuppressWarnings("unused")
public class Mapping
{
    private static final Joiner DOT_JOINER = Joiner.on('.').skipNulls();
    private static final Splitter DASH_SPLITTER = Splitter.on('-').trimResults().omitEmptyStrings();
    private static final Converter<String, String> CAMEL_TO_HYPHEN =
            CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_HYPHEN);

    private static final Map<Method, MethodInfo> methods = Maps.newConcurrentMap();
    private final static Function<MethodInfo, String> FIELDNAME_MAPPER = new Function<MethodInfo, String>()
    {
        @Nullable
        @Override
        public String apply(MethodInfo input)
        {
            return input.getFieldName();
        }
    };

    private Mapping()
    {
    }

    public static <T> T create(Class<? extends T> type)
    {
        return Reflection.newProxy(type, new MappingProxy(type, Maps.<String, Object>newConcurrentMap()));
    }

    public static <T> T create(Map<String, Object> properties, Class<? extends T> type)
    {
        return Reflection.newProxy(type, new MappingProxy(type, properties));
    }

    public static <T> T create(Map<String, Object> properties, String prefix, Class<? extends T> type)
    {
        return Reflection.newProxy(type, new MappingProxy(type, properties, prefix));
    }

    public static Map<String, Object> getMap(Object mapping)
    {
        try
        {
            MappingProxy handler = (MappingProxy) Proxy.getInvocationHandler(mapping);
            return handler.getMap();
        } catch (IllegalArgumentException e)
        {
            return null;
        }
    }

    private static String camelcaseToProperty(String name)
    {
        String converted = CAMEL_TO_HYPHEN.convert(name);
        Preconditions.checkArgument(converted != null, "错误的名字格式");
        assert converted != null;
        return DOT_JOINER.join(DASH_SPLITTER.splitToList(converted));
    }

    private static MethodInfo info(Method m)
    {
        MethodInfo info = methods.get(m);
        if (info == null)
        {
            info = createInfo(m);
            methods.put(m, info);
        }
        return info;
    }

    private static MethodInfo createInfo(Method method)
    {
        Class<?> returnType = method.getReturnType();
        Class<?>[] parameterTypes = method.getParameterTypes();

        boolean isGetter = false;
        String name = method.getName();
        String fieldName = null;
        String prefix = null;
        String prop = null;

        if (name.startsWith("is"))
        {
            isGetter = true;
            fieldName = name.substring(2);
        } else if (name.startsWith("get"))
        {
            isGetter = true;
            fieldName = name.substring(3);
        } else if (name.startsWith("set"))
        {
            isGetter = false;
            fieldName = name.substring(3);
        }

        if (fieldName == null)
        {
            // String camelCaseGetter()

            if (parameterTypes.length == 0 && returnType != void.class)
            {
                isGetter = true;
                fieldName = name;
            } else if (parameterTypes.length == 1)
            {
                // String/void/Self camelCaseSetter(String val)
                if (returnType == parameterTypes[0]
                        || returnType == void.class
                        || returnType.isAssignableFrom(method.getDeclaringClass()))
                {
                    isGetter = false;
                    fieldName = name;
                }
            }
        }

        Preconditions.checkArgument(fieldName != null, "无法分析方法 %s 的操作", method);

        if (method.isAnnotationPresent(Prefix.class))
        {
            prefix = method.getAnnotation(Prefix.class).value();
        }
        if (method.isAnnotationPresent(Prop.class))
        {
            prop = method.getAnnotation(Prop.class).value();
        }

        return new MethodInfo(name, fieldName, isGetter, prop, prefix, method);
    }

    @Documented
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Prop
    {
        String value();
    }

    @Documented
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Prefix
    {
        String value();
    }


    private static class MappingProxy extends AbstractInvocationHandler
    {
        private final Class<?> type;
        private final Map<String, Object> map;
        private final Map<String, String> keyMap = Maps.newHashMap();
        private final String prefix;
        private Function<MethodInfo, String> keyMapper = FIELDNAME_MAPPER;

        public MappingProxy(Class<?> type, Map<String, Object> map)
        {
            this(type, map, "");
        }

        public MappingProxy(Class<?> type, Map<String, Object> map, String prefix)
        {
            this.type = type;
            this.map = map;
            this.prefix = prefix;
            initialize();
        }

        public Map<String, Object> getMap()
        {
            return map;
        }

        public Class<?> getType()
        {
            return type;
        }

        public boolean isNestedType(Class type)
        {
            String name = type.getCanonicalName();
            return !(name.startsWith("java.") || name.startsWith("javax."));
        }

        public void initialize()
        {
            // 初始 getter 和 setter
            for (Method method : type.getDeclaredMethods())
            {
                MethodInfo info = info(method);
                keyMap.put(info.getFieldName(), keyMapper.apply(info));
            }
        }

        @SuppressWarnings("NullableProblems")
        @Override
        protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable
        {
            Class<?> returnType = method.getReturnType();
            MethodInfo info = info(method);
            String key = keyMap.get(info.getFieldName());


            if (info.isSetter())
            {
                Object old = map.put(key, args[0]);
                if (returnType.isAssignableFrom(type))
                    return proxy;
                if (returnType == void.class)
                    return null;
                return adapter(old, returnType);
            }
            if (info.isGetter())
                return adapter(map.get(key), returnType);

            throw new UnsupportedOperationException(method.toGenericString());
        }

        public Object adapter(Object value, Class<?> to)
        {
            if (value == null)
            {
                return null;
            }
            Class<?> from = value.getClass();
            if (to.isAssignableFrom(from))
            {
                return to.cast(value);
            }
            // TODO 处理类型转换
            return null;
        }

        @Override
        public String toString()
        {
            MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper(type);
            for (Map.Entry<String, Object> entry : map.entrySet())
            {
                helper.add(entry.getKey(), entry.getValue());
            }
            return helper.toString();
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (!(o instanceof MappingProxy)) return false;

            MappingProxy that = (MappingProxy) o;

            return map.equals(that.getMap());
        }

        @Override
        public int hashCode()
        {
            int result = super.hashCode();
            result = 31 * result + (map != null ? map.hashCode() : 0);
            return result;
        }
    }

    public static class MethodInfo
    {
        private final String methodName;
        private final String fieldName;
        private final boolean isGetter;
        private final String prop;
        private final String prefix;
        private final Method method;

        private MethodInfo(String methodName, String fieldName, boolean isGetter, String prop, String prefix, Method method)
        {
            this.methodName = methodName;
            this.fieldName = fieldName;
            this.isGetter = isGetter;
            this.prop = prop;
            this.prefix = prefix;
            this.method = method;
        }

        public String getFieldName()
        {
            return fieldName;
        }

        public String getMethodName()
        {
            return methodName;
        }

        public boolean isGetter()
        {
            return isGetter;
        }

        public boolean isSetter()
        {
            return !isGetter;
        }

        public String getProp()
        {
            return prop;
        }

        public String getPrefix()
        {
            return prefix;
        }
    }
}
