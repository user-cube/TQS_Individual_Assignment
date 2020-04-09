package pt.ua.tqs.airquality.services;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

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
@WebMvcTest(value = BreezoMeterService.class)
class BreezoMeterServiceTest {

    private BreezoMeterService breezoMeterService;

    private final Client client = ClientBuilder.newClient();
    private static final String baseURL = "https://api.breezometer.com/air-quality/v2/current-conditions?";

    @BeforeEach
    void setUp() {
        breezoMeterService = new BreezoMeterService();
    }

    @Test
    public void getAirConditionForExistingCityTest() {
        getAndStore("40.6405", "-8.6538");
        assertEquals(breezoMeterService.getAirConditions("40.6405", "-8.6538").keySet(), jsonReader().keySet());
    }

    public void getAndStore(String latitude, String longitude){
        try {
            JSONObject data = client
                    .target(baseURL + "lat=" + latitude +
                            "&lon=" + longitude +
                            "&key=e5c3a4bbfda4433eaaf91581c5a175e3" +
                            "&features=breezometer_aqi,local_aqi," +
                            "pollutants_concentrations,pollutants_aqi_information")
                    .request(MediaType.APPLICATION_JSON)
                    .get(JSONObject.class);
            Files.write(Paths.get("src/test/java/pt/ua/tqs/airquality/services/breezoGet.json"), data.toJSONString().getBytes());
        } catch (Exception exception){
            Logger.getLogger(BreezoMeterService.class.getName()).log(Level.SEVERE, null, exception);
        }
    }
    private JSONObject jsonReader() {
        JSONObject jsonObject;
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/test/java/pt/ua/tqs/airquality/services/breezoGet.json"));
            jsonObject = (JSONObject) obj;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return jsonObject;
    }
}