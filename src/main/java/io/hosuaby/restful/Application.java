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

    private static final String DEFAULT_PORT = "8080";

    public static void main(String[] args) {

        /* For Heroku: get port from environment */
        // TODO: check if possible to change default port variable of Spring
        //       Boot
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = DEFAULT_PORT;
        }
        System.setProperty("server.port", webPort);

        SpringApplication.run(Application.class, args);     // run!
    }

}
