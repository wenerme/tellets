package me.wener.telletsj.process;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLCommentMetaProcessor implements MetaProcessor
{
    private final static XMLCommentMetaProcessor INSTANCE = new XMLCommentMetaProcessor();

    private final static Pattern CONTENT_SPLITTER =
            Pattern.compile("^\\s*<!--\\s*(?<type>more|summary|paging)\\s*-->\\s*$", Pattern.MULTILINE);
    private final static Pattern SINGLE_META =
            Pattern.compile("^\\s*<!--(?<key>[^:]+):(?<value>.*?)-->\\s*$", Pattern.MULTILINE);
    private final static Pattern ALL_META =
            Pattern.compile("("+SINGLE_META.pattern()+")+", Pattern.MULTILINE);


    public static XMLCommentMetaProcessor getInstance()
    {
        return INSTANCE;
    }

    @Override
    public MetaData process(String content) throws ProcessException
    {
        String metaContent;
        String restContent;
        MetaData meta = new MetaData();
        {
            Matcher matcher = ALL_META.matcher(content);
            if (!matcher.find())
                throw new NoMetaException();

            metaContent = matcher.group();
            restContent = content.substring(matcher.end());
        }
        {
            Matcher matcher = SINGLE_META.matcher(metaContent);
            while (matcher.find())
            {
                String key = matcher.group("key").trim();
                String value = matcher.group("value").trim();
                meta.put(key, value);
            }
        }
        {
            Matcher matcher = CONTENT_SPLITTER.matcher(restContent);

            while (matcher.find())
            {
                String type = matcher.group("type");

            }

        }
        return meta;
    }
}
