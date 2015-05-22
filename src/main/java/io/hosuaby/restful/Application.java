package io.hosuaby.restful;

import io.hosuaby.restful.config.SimpleCorsFilter;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Main application class.
 *
 * @author Alexei KLENIN
 */
@SpringBootApplication
@EnableSwagger2                 // enable Swagger
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);     // run!
    }

    /**
     * @return simple CORS filter.
     */
    @Bean
    public Filter corsFilter() {
        return new SimpleCorsFilter();
    }

}
