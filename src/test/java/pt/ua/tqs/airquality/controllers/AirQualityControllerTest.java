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
import pt.ua.tqs.airquality.entities.CitiesCoordinates;
import pt.ua.tqs.airquality.services.BreezoMeterService;
import pt.ua.tqs.airquality.services.CacheService;
import pt.ua.tqs.airquality.tools.CityInfo;
import pt.ua.tqs.airquality.tools.ProcessJSON;

import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = AirQualityController.class)
class AirQualityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CacheService cacheService;

    @MockBean
    private CityInfo cityInfo;

    @MockBean
    private ProcessJSON processJSON;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AirQualityController(cacheService, cityInfo, processJSON))
                .alwaysExpect(forwardedUrl(null))
                .build();
    }

    @Test
    public void getAirConditionsFromExistingCityTest() throws Exception {
        Mockito.when(cacheService.getAirConditions("40.6405", "-8.6538", "Aveiro")).thenReturn(jsonReader());
        Mockito.when(cityInfo.coordinatesFromName("Aveiro")).thenReturn(coordinatesName("Aveiro"));
        Mockito.when(processJSON.processJSON(jsonReader())).thenReturn(jsonProcess(jsonReader()));

        mockMvc.perform(get("/airquality/" + "Aveiro"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasKey("indexes")))
                .andExpect(jsonPath("$.data", hasKey("pollutants")))
                .andExpect(jsonPath("$.data.pollutants", hasKey("no2")))
                .andExpect(jsonPath("$.data.pollutants.no2.concentration", hasKey("value")))
                .andExpect(jsonPath("$.data.pollutants", hasKey("o3")))
                .andExpect(jsonPath("$.data.pollutants.o3.concentration", hasKey("value")))
                .andExpect(jsonPath("$.data.pollutants", hasKey("pm10")))
                .andExpect(jsonPath("$.data.pollutants.pm10.concentration", hasKey("value")))
                .andExpect(jsonPath("$.data.pollutants", hasKey("pm25")))
                .andExpect(jsonPath("$.data.pollutants.pm25.concentration", hasKey("value")))
                .andExpect(jsonPath("$.data.pollutants", hasKey("so2")))
                .andExpect(jsonPath("$.data.pollutants.so2.concentration", hasKey("value")));

        MvcResult mock = mockMvc.perform(get("/airquality/" + "Aveiro"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());

    }

    @Test
    public void getAirConditionsFromNonExistingCityTest() throws Exception{
        Mockito.when(cacheService.getAirConditions(null, null, "Aveiras")).thenReturn(null);
        Mockito.when(cityInfo.coordinatesFromName("Aveiras")).thenReturn(null);

        mockMvc.perform(get("/airquality/" + "Aveiras"))
                .andDo(print())
                .andExpect(status().is(404));

        MvcResult mock = mockMvc.perform(get("/airquality/" + "Aveiras"))
                .andExpect(status().is(404))
                .andReturn();

        assertEquals(null,
                mock.getResponse().getContentType());

    }

    private String jsonReader() {
        JSONObject jsonObject;
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/test/java/pt/ua/tqs/airquality/cache/response.json"));
            jsonObject = (JSONObject) obj;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return jsonObject.toString();
    }

    private String [] coordinatesName(String city){
        String cityName = city.toUpperCase().replaceAll("\\s", "");
        String [] coordinates = new String[2];

        coordinates[0] = CitiesCoordinates.valueOf(cityName + "_LAT").toString();
        coordinates[1] = CitiesCoordinates.valueOf(cityName + "_LONG").toString();

        return coordinates;
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