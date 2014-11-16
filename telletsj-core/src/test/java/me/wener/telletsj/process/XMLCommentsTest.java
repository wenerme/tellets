package me.wener.telletsj.process;

import java.util.List;
import java.util.Map;
import me.wener.telletsj.BaseTest;
import me.wener.telletsj.process.processor.XMLCommentMetaProcessor;
import org.junit.Test;

public class XMLCommentsTest extends BaseTest
{
    public static void assertSimplePost(ContentInfo info)
    {
        Map<String, String> meta = info.getMeta();

        assert meta.get("title").equals("simple post, simple article");
        assert meta.get("date").equals("2014-11-01");
        assert meta.get("link").equals("start");
        List<String> category = info.list("category");
        assert category.contains("moon");
        assert category.contains("sun");
        assert category.size() == 2;

        assert info.section("more").equals("description here");
        assert info.getRestContent().equals("detail here");
        assert meta.get("state").equals("publish");
    }

    @Test
    public void test() throws ProcessException
    {
        String content = getContent("res:me/wener/telletsj/process/SimplePost.md");
        XMLCommentMetaProcessor processor = new XMLCommentMetaProcessor();
        ContentInfo info = processor.process(content);

        assertSimplePost(info);
    }
}
