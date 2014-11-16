package me.wener.telletsj.core;

import static me.wener.telletsj.util.strtotime.strtotime;

import com.google.common.base.Suppliers;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.net.URI;
import me.wener.telletsj.collect.CollectionException;
import me.wener.telletsj.collect.CollectionService;
import me.wener.telletsj.collect.impl.CollectModule;
import me.wener.telletsj.collect.impl.SourceContent;
import me.wener.telletsj.data.Article;
import me.wener.telletsj.data.ArticleInfo;
import me.wener.telletsj.data.ArticleState;
import me.wener.telletsj.data.DataService;
import me.wener.telletsj.data.cq.CQDataModule;
import me.wener.telletsj.data.impl.ArticleBuilder;
import me.wener.telletsj.process.ContentInfo;
import me.wener.telletsj.process.ProcessException;
import me.wener.telletsj.process.ProcessModule;
import me.wener.telletsj.process.ProcessService;
import org.junit.Before;
import org.junit.Test;

public class CoreTest
{
    @Inject
    DataService dataService;
    @Inject
    CollectionService collectionService;
    @Inject
    ProcessService processService;

    @Before
    public void setup()
    {
        Injector injector = Guice
                .createInjector(new TelletsJModule());
        CollectModule collectModule = injector.getInstance(CollectModule.class);
        injector = injector.createChildInjector(collectModule, injector.getInstance(ProcessModule.class), injector
                .getInstance(CQDataModule.class));

        injector.injectMembers(this);
    }

    @Test
    public void test() throws CollectionException, ProcessException
    {
        SourceContent content = collectionService.getContent(URI.create("res:me/wener/telletsj/process/SimplePost.md"));
        ContentInfo contentInfo = processService
                .tryProcess(content.getFilename(), Suppliers.ofInstance(content.getContent()));
        ArticleBuilder builder = dataService.createArticleBuilder();

        Article article = builder.content(contentInfo.getRawContent())
                                 .sha(content.getSha())
                                 .description(contentInfo.section("more"))
                                 .link(contentInfo.meta("link"))
                                 .title(contentInfo.meta("title"))
                                 .state(ArticleState.fromString(contentInfo.meta("state")))
                                 .timestamp(strtotime(contentInfo.meta("date")).getTime())
                                 .article();
        ArticleInfo info = builder.info();

        dataService.store(article, info);

        System.out.println(article);
        System.out.println(info);
    }
}
