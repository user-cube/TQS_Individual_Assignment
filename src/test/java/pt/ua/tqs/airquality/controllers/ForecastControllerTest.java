package pt.ua.tqs.airquality.controllers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pt.ua.tqs.airquality.services.BreezoMeterService;
import pt.ua.tqs.airquality.services.CacheService;
import pt.ua.tqs.airquality.tools.ProcessJSON;

import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = ForecastController.class)
public class ForecastControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CacheService cacheService;

    @MockBean
    private ProcessJSON processJSON;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ForecastController(cacheService, processJSON))
                .alwaysExpect(forwardedUrl(null))
                .build();
    }

    @Test
    public void getForecastForExistingCityTest() throws Exception {
        Mockito.when(cacheService.getForecast("Aveiro")).thenReturn(jsonReader());
        Mockito.when(processJSON.processJSON(jsonReader())).thenReturn(jsonProcess(jsonReader()));
        mockMvc.perform(get("/forecast/" + "Aveiro"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("data")))
                .andExpect(jsonPath("$.data", hasSize(5)))
                .andExpect(jsonPath("$.data[0]", hasKey("tMax")))
                .andExpect(jsonPath("$.data[0]", hasKey("tMin")))
                .andExpect(jsonPath("$.data[0]", hasKey("classWindSpeed")))
                .andExpect(jsonPath("$.data[0]", hasKey("predWindDir")))
                .andExpect(jsonPath("$.data[0]", hasKey("forecastDate")));

        MvcResult mock = mockMvc.perform(get("/forecast/" + "Aveiro"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void getForecastForNonExistingCityTest() throws Exception {
        Mockito.when(cacheService.getForecast("Aveiras")).thenReturn(null);
        Mockito.when(processJSON.processJSON(jsonReader())).thenReturn(null);

        mockMvc.perform(get("/forecast/" + "Aveiras"))
                .andDo(print())
                .andExpect(status().is(404));

        MvcResult mock = mockMvc.perform(get("/forecast/" + "Aveiras"))
                .andExpect(status().is(404))
                .andReturn();

        assertEquals(null,
                mock.getResponse().getContentType());
    }


    private String jsonReader() {
        JSONObject jsonObject;
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/test/java/pt/ua/tqs/airquality/controllers/ipma.json"));
            jsonObject = (JSONObject) obj;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return jsonObject.toString();
    }

    public JSONObject jsonProcess(String data){
        JSONObject json;
        try {
            json = (JSONObject) new JSONParser().parse(data);
        } catch (ParseException exception) {
            Logger.getLogger(BreezoMeterService.class.getName()).log(Level.SEVERE, null, exception);
            return null;
        }
        return json;
    }

}
