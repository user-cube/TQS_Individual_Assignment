package pt.ua.tqs.airquality.services;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pt.ua.tqs.airquality.tools.CityInfo;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = IPMAService.class)
class IPMAServiceTest {

    @MockBean
    private CityInfo cityInfo;

    private IPMAService ipmaService;

    private final Client client = ClientBuilder.newClient();
    private static final String cityURL = "http://api.ipma.pt/open-data/distrits-islands";
    private static final String weatherURL = "http://api.ipma.pt/open-data/forecast/meteorology/cities/daily/";

    @BeforeEach
    public void setUp(){
        ipmaService = new IPMAService(cityInfo);
    }

    @Test
    public void getForecastForExistingCityTest() {
        Mockito.when(cityInfo.getCityCode("Aveiro", cityURL )).thenReturn("1010500");
        getAndStore("Aveiro");
        assertEquals(ipmaService.getForecast("Aveiro").keySet(), jsonReader().keySet());
    }

    @Test
    public void getForecastForNonExistingCityTest(){
        Mockito.when(cityInfo.getCityCode("Aveiras", cityURL )).thenReturn(String.valueOf(1213421412));
        if (getAndStore("Aveiras"))
            assertEquals(ipmaService.getForecast("Aveiras"), null);
    }

    public Boolean getAndStore(String city){
        try {
            JSONObject data = client
                    .target(weatherURL + cityInfo.getCityCode(city, cityURL))
                    .request(MediaType.APPLICATION_JSON)
                    .get(JSONObject.class);
            Files.write(Paths.get("src/test/java/pt/ua/tqs/airquality/services/ipmaGet.json"), data.toJSONString().getBytes());
            return true;
        } catch (Exception exception){
            Logger.getLogger(BreezoMeterService.class.getName()).log(Level.SEVERE, null, exception);
            return false;
        }
    }
    private JSONObject jsonReader() {
        JSONObject jsonObject;
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/test/java/pt/ua/tqs/airquality/services/ipmaGet.json"));
            jsonObject = (JSONObject) obj;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return jsonObject;
    }
}