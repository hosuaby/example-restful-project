package io.hosuaby.restful.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configuration of Spring MVC.
 *
 * @author Alexei KLENIN
 */
@Configuration
@EnableWebMvc                   // tells that this is Spring MVC configuration
@ComponentScan(
        "io.hosuaby.restful.controllers"    // base package for controllers
)
public class WebMvcConfig extends WebMvcConfigurerAdapter {}
