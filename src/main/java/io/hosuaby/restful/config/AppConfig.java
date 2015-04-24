package io.hosuaby.restful.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuation defining shared resources visible to all other web components,
 * servlets etc. and for cross-cutting concerns like security.
 *
 * @author Alexei KLENIN
 */
@Configuration
@ComponentScan("io.hosuaby.restful.services")
public class AppConfig {}
