package pt.ua.tqs.airquality.services;

import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

@Service
public class BreezoMeterService {

    private final Client client = ClientBuilder.newClient();

    public JSONObject getAirConditions(String latitude, String longitude) {
        return client.target("https://api.breezometer.com/air-quality/v2/current-conditions?" + "lat=" + latitude +
                        "&lon=" + longitude +
                        "&key=e5c3a4bbfda4433eaaf91581c5a175e3" +
                        "&features=breezometer_aqi,local_aqi,pollutants_concentrations,pollutants_aqi_information").request(MediaType.APPLICATION_JSON).get(JSONObject.class);
    }
}
