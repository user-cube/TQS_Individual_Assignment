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

    private CityInfo cityInfo;

    public IPMAService(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    public JSONObject getForecast(String city) {
        try {
            return client.target("http://api.ipma.pt/open-data/forecast/meteorology/cities/daily/" + cityInfo.getCityCode(city, "http://api.ipma.pt/open-data/distrits-islands")).request(MediaType.APPLICATION_JSON).get(JSONObject.class);
        } catch (Exception exception){
            Logger.getLogger(IPMAService.class.getName()).log(Level.SEVERE, null, exception);
            return null;
        }
    }

}
