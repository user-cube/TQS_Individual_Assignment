package pt.ua.tqs.airquality.services;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import pt.ua.tqs.airquality.tools.CityInfo;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class IPMAService {
    private final Client client = ClientBuilder.newClient();
    private static final String weatherURL = "http://api.ipma.pt/open-data/forecast/meteorology/cities/daily/";
    private static final String cityURL = "http://api.ipma.pt/open-data/distrits-islands";

    private CityInfo cityInfo;

    public IPMAService(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    public JSONObject getForecast(String city) {
        try {
            JSONObject data = client
                    .target(weatherURL + cityInfo.getCityCode(city, cityURL))
                    .request(MediaType.APPLICATION_JSON)
                    .get(JSONObject.class);
            return data;
        } catch (Exception exception){
            Logger.getLogger(IPMAService.class.getName()).log(Level.SEVERE, null, exception);
            return null;
        }
    }

}
