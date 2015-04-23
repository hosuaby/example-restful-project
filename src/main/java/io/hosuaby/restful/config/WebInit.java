package io.hosuaby.restful.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Configuration of web initialization in Tomcat 8. Java replacement for
 * web.xml file.
 *
 * @author Alexei KLENIN
 */
public class WebInit implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {

        /* Create the root Spring application context */
        AnnotationConfigWebApplicationContext rootContext =
                new AnnotationConfigWebApplicationContext();

        /* Set displayed name of the application */
        rootContext.setDisplayName("Example Restful Project");

        /* Registers the application configuration with the root context */
        rootContext.register(AppConfig.class);

        /* Creates the Spring Container shared by all Servlets and Filters */
        container.addListener(new ContextLoaderListener(rootContext));

        /* Create context for Spring MVC dispatcher servlet */
        AnnotationConfigWebApplicationContext dispatcherContext =
                           new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(WebMvcConfig.class);

        /* Register and map the dispatcher servlet */
        ServletRegistration.Dynamic dispatcher = container.addServlet(
                "dispatcher",
                new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);     // load this servlet on startup
        dispatcher.addMapping("/");         // every request go into
    }

}
