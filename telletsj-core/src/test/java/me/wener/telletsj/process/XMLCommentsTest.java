package me.wener.telletsj.process;

import com.google.common.base.Splitter;
import java.util.List;
import java.util.Map;
import me.wener.telletsj.BaseTest;
import me.wener.telletsj.process.processor.XMLCommentMetaProcessor;
import org.junit.Test;

public class XMLCommentsTest extends BaseTest
{
    @Test
    public void test() throws ProcessException
    {
        String content = getContent("res:me/wener/telletsj/process/SimplePost.md");
        XMLCommentMetaProcessor processor = new XMLCommentMetaProcessor();
        ContentInfo info = processor.process(content);
        Map<String, String> meta = info.meta();

        assert meta.get("title").equals("simple post, simple article");
        assert meta.get("date").equals("2014-11-01");
        assert meta.get("link").equals("start");
        Splitter COMMA_SPLITTER = Splitter.on(',').omitEmptyStrings().trimResults();
        List<String> category = COMMA_SPLITTER.splitToList(meta.get("category"));
        assert category.contains("moon");
        assert category.contains("sun");
        assert category.size() == 2;

        assert info.sectionOne("more").equals("description here");
        assert info.restContent().equals("detail here");
    }
}
