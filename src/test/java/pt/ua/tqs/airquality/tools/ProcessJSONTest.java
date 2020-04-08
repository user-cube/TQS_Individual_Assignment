package pt.ua.tqs.airquality.tools;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import pt.ua.tqs.airquality.services.BreezoMeterService;

import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = ProcessJSON.class)
class ProcessJSONTest {

    private ProcessJSON processJSON;

    @BeforeEach
    public void setUp(){
        processJSON = new ProcessJSON();
    }

    @Test
    public void niceJSONObjectTest() {
        assertNotEquals(null, processJSON.processJSON(jsonReader(true)));
        assertEquals(processJSON.processJSON(jsonReader(true)), jsonProcess(jsonReader(true)));
    }

    @Test
    public void badJSONObjectTest(){
        assertEquals(null, processJSON.processJSON(jsonReader(false)));
    }

    private String jsonReader(Boolean isNice) {
        JSONObject jsonObject;
        JSONParser parser = new JSONParser();
        try {
            if (isNice) {
                Object obj = parser.parse(new FileReader("src/test/java/pt/ua/tqs/airquality/controllers/ipma.json"));
                jsonObject = (JSONObject) obj;
            } else {
                Object obj = parser.parse(new FileReader("src/test/java/pt/ua/tqs/airquality/tools/bad.json"));
                jsonObject = (JSONObject) obj;
            }
        } catch (Exception e) {
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