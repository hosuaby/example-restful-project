package io.hosuaby.restful.tests.config;

import io.hosuaby.restful.PortHolder;
import io.hosuaby.restful.domain.Teapot;
import io.hosuaby.restful.mappers.TeapotMapper;
import io.hosuaby.restful.mappers.TeapotMapperImpl;
import io.hosuaby.restful.services.TeapotCommandService;
import io.hosuaby.restful.services.TeapotCrudService;
import io.hosuaby.restful.services.exceptions.teapots.TeapotNotExistsException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

/**
 * Configuration for tests of MVC controllers.
 */
@Configuration
@ComponentScan(basePackages = {
        "io.hosuaby.restful.controllers",
        "io.hosuaby.restful.domain.validators" })
public class WebMvcTestsConfig {

    /** Teapot "Mouse" */
    private static  final Teapot MOUSE = new Teapot(
            "mouse", "Mouse", "Tefal", Teapot.L0_3);

    /** Teapot "Einstein" */
    private static final Teapot EINSTEIN = new Teapot(
            "einstein", "Einstein", "Sony", Teapot.L3);

    /** Teapot "Nemezis" */
    private static final Teapot NEMEZIS = new Teapot(
            "nemezis", "Nemezis", "Philips", Teapot.L10);

    /** Teapots to return by {@code TeapotCrudService} mock */
    private static final Set<Teapot> TEAPOTS = new HashSet<Teapot>() {
        private static final long serialVersionUID = 1L;
        {
            add(MOUSE);
            add(EINSTEIN);
            add(NEMEZIS);
        }
    };

    /**
     * @return {@link TeapotMapper} implementation.
     */
    @Bean
    public TeapotMapper teapotMapper() {
        return new TeapotMapperImpl();
    }

    /**
     * @return mock of {@link TeapotCrudService}
     */
    @Bean
    public TeapotCrudService teapotCrudService() {
        TeapotCrudService mockService = mock(TeapotCrudService.class);

        /* Mocked findAll method */
        when(mockService.findAll())
                .thenReturn(TEAPOTS);

        /* Mock returns of find method invocations */
        try {
            when(mockService.find(anyString()))
                    .thenAnswer(invocationOnMock -> {
                            final String teapotId = (String) invocationOnMock
                                    .getArguments()[0];

                            switch (teapotId) {
                                case "mouse":
                                    return MOUSE;
                                case "einstein":
                                    return EINSTEIN;
                                case "nemezis":
                                    return NEMEZIS;
                                default:
                                    throw new TeapotNotExistsException(
                                            teapotId);
                            }
                        });
        } catch (TeapotNotExistsException e) {
            e.printStackTrace();
        }

        return mockService;
    }

    /**
     * @return mock of {@link TeapotCommandService}
     */
    @Bean
    public TeapotCommandService teapotCommandService() {
        return mock(TeapotCommandService.class);
    }

    /**
     * @return port holder
     */
    @Bean
    public PortHolder portHolder() {
        return new PortHolder();
    }

}
