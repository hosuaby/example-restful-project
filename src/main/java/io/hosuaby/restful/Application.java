package io.hosuaby.restful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

}
