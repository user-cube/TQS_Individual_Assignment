package pt.ua.tqs.airquality.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = CoordinatesController.class)
public class CoordinatesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(new CoordinatesController())
                .alwaysExpect(forwardedUrl(null))
                .build();
    }

    @Test
    public void getCoordinatesFromExistingCityTest() throws Exception {
        mockMvc.perform(get("/coordinates/" + "Aveiro"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("latitude")))
                .andExpect(jsonPath("$", hasKey("longitude")));

        MvcResult mock = mockMvc.perform(get("/coordinates/" + "Aveiro"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void getCoordinatesFromNonExistingCityTest() throws Exception {
        mockMvc.perform(get("/coordinates/" + "Aveiras"))
                .andDo(print())
                .andExpect(status().is(204));

        MvcResult mock = mockMvc.perform(get("/coordinates/" + "Aveiras"))
                .andExpect(status().is(204))
                .andReturn();

        assertEquals("Nothing to show.",
                mock.getResponse().getContentAsString());
    }
}
