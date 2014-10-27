package me.wener.telletsj.collect;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.net.URI;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class TestService
{
    private Injector injector;
    @Inject
    private CollectionService service;
    @Inject
    private SourceProviderManager manager;
    @Before
    public void setup()
    {
        injector = Guice.createInjector(new CollectModule());
        injector.injectMembers(this);
    }

    @Test
    public void testGetGitHub() throws CollectionException
    {
        SourceContent content = service.getContent(URI.create("github:wenerme/wener/README.md"));
        System.out.println(content.getSha());
        System.out.println(content.getContent());
    }
}
