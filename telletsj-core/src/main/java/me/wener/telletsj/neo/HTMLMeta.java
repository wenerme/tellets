package me.wener.telletsj.neo;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLMeta
{

    private final static Pattern SECTION_SPLITTER =
            Pattern.compile("^\\s*<!---*\\s*(?<type>more|summary|paging)\\s*-*?-->\\s*$", Pattern.MULTILINE);
    private final static Pattern SINGLE_META =
            Pattern.compile("^\\s*<!---*(?<key>[^:]+):(?<value>.*?)-*?-->\\s*$", Pattern.MULTILINE);
    private final static Pattern ALL_META = Pattern.compile("(" + SINGLE_META.pattern() + ")+", Pattern.MULTILINE);

    public static boolean isMatch(String content)
    {
        return content.startsWith("<!--");
    }

    public static Map<String, Object> process(final String content)
    {
        String metaContent;
        String restContent;

        Map<String, Object> data = Maps.newHashMap();
        data.put("content", content);

        {
            Matcher matcher = ALL_META.matcher(content);
            if (!matcher.find())
            {
                return null;
            }
            int lastEnd = matcher.end();
            while (matcher.find())
                lastEnd = matcher.end();

            metaContent = content.substring(0, lastEnd);
            restContent = content.substring(lastEnd);
        }
        {
            Matcher matcher = SINGLE_META.matcher(metaContent);
            while (matcher.find())
            {
                String key = matcher.group("key").trim();
                String value = matcher.group("value").trim();
                data.put(key, value);
            }
        }
        {
            Matcher matcher = SECTION_SPLITTER.matcher(restContent);
            int lastEnd = 0;
            while (matcher.find())
            {
                String type = matcher.group("type");
                String sectionContent = restContent
                        .substring(lastEnd, matcher.start())
                        .trim();
                data.put("section:" + type, sectionContent);
                lastEnd = matcher.end();
            }

            if (restContent.length() != lastEnd)
                data.put("restContent", restContent.substring(lastEnd));
        }
        return data;
    }
}
