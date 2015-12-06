package io.hosuaby.restful.config;

import io.hosuaby.restful.TeapotMappingHandler;
import io.hosuaby.restful.mappers.TeapotMapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * Customized Spring MVC configuration.
 */
@Configuration
// TODO: Create new bean that listens application context
public class WebMvcConfig extends WebMvcConfigurationSupport
        implements ApplicationListener {

    /** Teapot mapper */
    @Autowired
    private TeapotMapper mapper;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            ApplicationContext applicationContext = ((ContextRefreshedEvent) event).getApplicationContext();
            RequestMappingHandlerAdapter adapter = applicationContext.getBean(RequestMappingHandlerAdapter.class);

            TeapotMappingHandler handler = new TeapotMappingHandler(
                    mapper, getMessageConverters());

            List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();
            argumentResolvers.add(handler);
            argumentResolvers.addAll(adapter.getArgumentResolvers());

            adapter.setArgumentResolvers(argumentResolvers);

            List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>();
            handlers.add(handler);  // must be first
            handlers.addAll(adapter.getReturnValueHandlers());

            adapter.setReturnValueHandlers(handlers);
        }
    }

}
