package me.wener.telletsj;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import me.wener.telletsj.api.Article;
import me.wener.telletsj.util.Mapping;
import org.junit.Test;

public class MappingTest
{
    @Test
    public void test()
    {
        Article article = Mapping.create(Article.class);
        System.out.println(article
                        .sha("sha")
                        .title("title")
                        .content("content")
                        .categories(Lists.newArrayList("wener", "s"))
        );
        System.out.println(Mapping.getMap(article));
        assertEquals("sha", article.sha());
        assertTrue(Iterables.elementsEqual(Lists.newArrayList("wener", "s"),
                article.categories()));
    }
}
