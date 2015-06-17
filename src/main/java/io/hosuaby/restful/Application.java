package io.hosuaby.restful;

import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Main application class.
 */
@SpringBootApplication
@EnableSwagger2                 // enable Swagger
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);     // run!
    }

    /**
     * @return randomizer
     */
    @Bean
    public Random randomizer() {
        return new Random();
    }

}
