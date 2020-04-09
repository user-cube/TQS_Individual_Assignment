package pt.ua.tqs.airquality.services;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pt.ua.tqs.airquality.cache.Cache;

import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = CacheService.class)
class CacheServiceTest {

    @MockBean
    private BreezoMeterService breezoMeterService;

    @MockBean
    private IPMAService ipmaService;

    private Cache cache;

    private CacheService cacheService;

    @BeforeEach
    public void setUp() {
        cache = new Cache(20, 20, 15);
        cacheService = new CacheService(breezoMeterService, ipmaService);
    }

    @Test
    public void getAirConditionsForNonSavedCityTest() {
        Mockito.when(breezoMeterService.getAirConditions("40.6405", "-8.6538")).thenReturn(jsonProcess(jsonReader()));
        assertEquals(cacheService.getAirConditions("40.6405", "-8.6538", "Aveiro"), jsonReader());
    }

    @Test
    public void getAirConditionsForSavedCityTest() {
        Mockito.when(breezoMeterService.getAirConditions("40.6405", "-8.6538")).thenReturn(jsonProcess(jsonReader()));
        cache.put("Aveiro", jsonProcess(jsonReader()));
        assertEquals(cacheService.getAirConditions("40.6405", "-8.6538", "Aveiro"), jsonReader());
    }


    @Test
    public void getForecastForNonSavedCityTest() {
        Mockito.when(ipmaService.getForecast("Aveiro")).thenReturn(jsonProcess(jsonReader()));
        assertEquals(cacheService.getForecast("Aveiro"), jsonReader());
    }

    @Test
    public void getForecastForSavedCityTest() {
        Mockito.when(ipmaService.getForecast("Aveiro")).thenReturn(jsonProcess(jsonReader()));
        cache.put("AveiroFORECAST", jsonProcess(jsonReader()));
        assertEquals(cacheService.getForecast("Aveiro"), jsonReader());
    }

    @Test
    public void getForecastForNonExistingCity() {
        Mockito.when(ipmaService.getForecast("Aveiro")).thenReturn(null);
        assertEquals(null, cacheService.getForecast("Aveiro"));
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