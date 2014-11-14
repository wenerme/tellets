package me.wener.telletsj.process;

import com.google.common.base.Suppliers;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import me.wener.telletsj.BaseTest;
import org.junit.Before;
import org.junit.Test;

public class TestService extends BaseTest
{
    @Inject
    private ProcessService service;

    @Before
    public void setup()
    {
        Injector injector = Guice.createInjector();
        injector = injector.createChildInjector(injector.getInstance(ProcessModule.class));
        injector.injectMembers(this);
    }

    @Test
    public void test() throws ProcessException
    {
        String content = getContent("res:me/wener/telletsj/process/SimplePost.md");
        ContentInfo info = service.tryProcess("SimplePost.md", Suppliers.ofInstance(content));
        XMLCommentsTest.assertSimplePost(info);
    }

    @Test(expected = ProcessException.class)
    public void testNoMeta() throws ProcessException
    {
        service.tryProcess("SimplePost.md", Suppliers.ofInstance("there is no meta data"));
    }

    @Test
    public void testCanNotProcess() throws ProcessException
    {
        assert service.tryProcess("SimplePost.no-type-match", Suppliers.ofInstance("there is no meta data")) == null;
    }
}
