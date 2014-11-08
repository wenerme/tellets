package me.wener.telletsj.data.cq;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import me.wener.telletsj.data.Article;
import me.wener.telletsj.data.ArticleState;
import me.wener.telletsj.data.DataService;
import org.junit.Before;
import org.junit.Test;

public class CQTest
{

    private DataService service;

    @Test
    public void testOrderByDate()
    {
        {
            ArrayList<Article> articles = Lists.newArrayList(service.getArticleOrderByDate(0, 5, false));
            assert articles.size() == 5;
            assertN(articles.get(0), 0);
            assertN(articles.get(4), 4);
        }
        {
            ArrayList<Article> articles = Lists.newArrayList(service.getArticleOrderByDate(0, 5, true));
            assert articles.size() == 5;
            assertN(articles.get(0), 99);
            assertN(articles.get(4), 95);
        }
        {
            ArrayList<Article> articles = Lists.newArrayList(service.getArticleOrderByDate(2, 5, false));
            assert articles.size() == 5;
            assertN(articles.get(0), 2);
            assertN(articles.get(4), 6);
        }
    }

    @Test
    private void testLinkAndSha()
    {
        Article a = service.getArticleByLink("link-67");
        Article b = service.getArticleBySha("sha-67");
        assert a != null;
        assert a.equals(b);
        int i = 67;
        assertN(a, i);
    }

    @Before
    public void setup()
    {
        service = new CQDataService();
        for (int i = 0; i < 100; i++)
        {
            Article article = service
                    .createArticleBuilder()
                    .content("content-" + i)
                    .link("link-" + i)
                    .sha("sha-" + i)
                    .title("title-" + i)
                    .state(ArticleState.PUBLISH)
                    .description("description-" + i)
                    .timestamp((long) i)
                    .build();
            service.store(article, service.getArticleInfo(article));
        }
    }

    private void assertN(Article a, int i)
    {
        assert a.getContent().equals("content-" + i);
        assert a.getLink().equals("link-" + i);
        assert a.getSha().equals("sha-" + i);
        assert a.getTitle().equals("title-" + i);
        assert a.getDescription().equals("description-" + i);
        assert a.getState().equals(ArticleState.PUBLISH);
        assert a.getTimestamp() == i;
    }
}
