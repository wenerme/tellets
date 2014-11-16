package me.wener.telletsj.util.guava;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import lombok.ToString;

/**
 * 以 ',' '=' 分割的选项
 */
@ToString
public class Option
{
    private final static Splitter COMMA_SPLITTER = Splitter.on(',')
                                                           .omitEmptyStrings()
                                                           .trimResults();

    private final static Splitter EQ_SPLITTER = Splitter.on('=')
                                                        .omitEmptyStrings()
                                                        .trimResults();

    private final List<String> flags = Lists.newArrayList();
    private final Map<String, String> options = Maps.newHashMap();

    public static Option parse(String opt)
    {
        Option option = new Option();
        for (String s : COMMA_SPLITTER.split(opt))
        {
            List<String> parts = EQ_SPLITTER.splitToList(s);
            Preconditions.checkArgument(parts.size() > 0 && parts.size() <= 2, "Wrong option %s", s);
            if (parts.size() == 1)
                option.flags.add(s);
            else
                option.options.put(parts.get(0), parts.get(1));
        }
        return option;
    }

    public static void main(String[] args)
    {
        Option option = parse("op=add,times=123,log,debug, threads = 10");
        assert option.get("op").equals("add");
        assert option.get("times").equals("123");
        assert option.get("threads").equals("10");
        assert option.contain("log");
        assert option.contain("debug");
        assert option.tryGet("time", "times").equals("123");
    }

    /**
     * @param flag 标志名
     * @return 是否包含标志
     */
    public boolean contain(String flag)
    {
        return flags.contains(flag);
    }

    /**
     * @param opt 选项名
     * @return 选项值
     */
    public String get(String opt)
    {
        return options.get(opt);
    }

    /**
     * @param opts 多个键值
     * @return 如果选项为 {@code null} 则尝试下一个,直到非 {@code null} 或尝试完
     */
    public String tryGet(String... opts)
    {
        for (String opt : opts)
        {
            String r = get(opt);
            if (r != null)
                return r;
        }
        return null;
    }

    /**
     * @param opt 选项名
     * @param def 如果选项未定义,则返回该默认值
     * @return 选项值
     */
    public String get(String opt, String def)
    {
        return Strings.isNullOrEmpty(get(opt)) ? def : get(opt);
    }
}
