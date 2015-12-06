package io.hosuaby.restful.tests;

import io.hosuaby.restful.config.WebMvcConfig;
import io.hosuaby.restful.controllers.TeapotCrudController;
import io.hosuaby.restful.tests.config.WebMvcTestsConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests of {@link TeapotCrudController}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = {
        WebMvcTestsConfig.class,
        WebMvcConfig.class })
public class TeapotCrudControllerTests {

    /** Context */
    @Autowired
    private WebApplicationContext wac;

    /** Mock MVC */
    private MockMvc mockMvc;

    /**
     * Creates a new Mock MVC.
     */
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * Test of find all teapots.
     */
    @Test
    public void testFindAllTeapots() throws Exception {
        mockMvc
            .perform(get("/crud/teapots/"))
            .andExpect(status().isOk())
            .andExpect(content()
                    .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    /**
     * Test of find one teapot by id.
     */
    @Test
    public void testFindExistingTeapot() throws Exception {
        mockMvc
                .perform(get("/crud/teapots/mouse"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("mouse")));
    }

    /**
     * Test of attempt to find not existing teapot.
     */
    @Test
    public void testFindNotExistingTeapot() throws Exception {
        mockMvc
                .perform(get("/crud/teapots/unknown"))
                .andExpect(status().isNotFound());
    }

}
