package pt.ua.tqs.airquality.tools;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import pt.ua.tqs.airquality.services.BreezoMeterService;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ProcessJSON {

    public JSONObject processJSON(String data){
        JSONObject json;
        try {
            json = (JSONObject) new JSONParser().parse(data);
        } catch (Exception exception) {
            Logger.getLogger(BreezoMeterService.class.getName()).log(Level.SEVERE, null, exception);
            return null;
        }
        return json;
    }
}
