package io.hosuaby.restful.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration of Spring MVC.
 *
 * @author Alexei KLENIN
 */
@Configuration
@EnableWebMvc                   // tells that this is Spring MVC configuration
@EnableSwagger2                 // enable Swagger
@ComponentScan({
        "io.hosuaby.restful.controllers",    // base package for controllers
        "io.hosuaby.restful.services"        // base package for services
})
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * Allow static resource requests to be handled by the container's default
     * Servlet.
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    }

}
