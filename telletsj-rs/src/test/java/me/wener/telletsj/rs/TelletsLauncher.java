package me.wener.telletsj.rs;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.wener.telletsj.rs.resources.HiApp;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jdkhttp.JdkHttpHandlerContainer;
import org.glassfish.jersey.server.ContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;

public class TelletsLauncher
{

    public static final String ROOT_PATH = "tj";
    private static final URI BASE_URI = URI.create("http://localhost:8080/");

    @Test
    public void test() throws Exception
    {
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(org.eclipse.jetty.servlet.DefaultServlet.class, "/");
//        context.addFilter(AppFilter.class, "/*", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST));
        server.setHandler(context);
        server.start();
        server.join();
    }

    @Test
    public void playJersey()
    {
        JdkHttpHandlerContainer container = ContainerFactory
                .createContainer(JdkHttpHandlerContainer.class, new TelletsApplication());


    }

    @Test
    public void testAPI()
    {

    }

    @Test
    public void testSimple()
    {
        try
        {
            System.out.println("\"Hello World\" Jersey Example App");

            final ResourceConfig resourceConfig = new ResourceConfig(HiApp.class);
            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig);

            System.out.println(String.format("Application started.\nTry out %s%s\nHit enter to stop it...",
                    BASE_URI, ROOT_PATH));
            System.in.read();
            server.shutdownNow();
        } catch (IOException ex)
        {
            Logger.getLogger(TelletsLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
