package io.hosuaby.restful.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Root application configuation defining shared resources visible to all other
 * web components, servlets etc. and for cross-cutting concerns like security.
 */
@Configuration
@ComponentScan("io.hosuaby.broker.services")
public class AppConfig {}
