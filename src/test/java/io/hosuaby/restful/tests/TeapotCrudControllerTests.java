package io.hosuaby.restful.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hosuaby.cakemold.CakeMold;
import io.hosuaby.restful.config.WebMvcConfig;
import io.hosuaby.restful.controllers.TeapotCrudController;
import io.hosuaby.restful.domain.Teapot;
import io.hosuaby.restful.mappings.TeapotMapping;
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
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    /** Jackson object mapper */
    private final ObjectMapper objectMapper = new ObjectMapper();

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
    public void testFindAllTeapots() {
        try {
            mockMvc
                .perform(get("/crud/teapots/"))
                .andExpect(status().isOk())
                .andExpect(content()
                    .contentTypeCompatibleWith(
                        MediaType.APPLICATION_JSON));
        } catch (Exception unexpectedException) {
          fail(unexpectedException.getMessage());
        }
    }

    /**
     * Test of find one teapot by id.
     */
    @Test
    public void testFindExistingTeapot() {
        try {
            mockMvc
                .perform(get("/crud/teapots/mouse"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("mouse")));
        } catch (Exception unexpectedException) {
          fail(unexpectedException.getMessage());
        }
    }

    /**
     * Test of attempt to find not existing teapot.
     */
    @Test
    public void testFindNotExistingTeapot() {
        try {
          mockMvc
              .perform(get("/crud/teapots/unknown"))
              .andExpect(status().isNotFound());
        } catch (Exception unexpectedException) {
          fail(unexpectedException.getMessage());
        }
    }

   /**
    * Test of attempt to create a new teapot.
    */
    @Test
    public void testAddNewTeapot() {
      TeapotMapping newTeapot = CakeMold.of(TeapotMapping.class)
          .set("id", "shakespeare")
          .set("name", "Shakespeare")
          .set("brand", "PowerHeat")
          .set("volume", Teapot.L0_3)
          .cook();

      try {
          mockMvc
              .perform(post("/crud/teapots/")
                  .content(objectMapper.writeValueAsString(newTeapot))
                  .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isCreated());
      } catch (Exception unexpectedException) {
        fail(unexpectedException.getMessage());
      }
    }

}
