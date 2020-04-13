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
                        "&key=" + System.getenv("BREEZOMETER_KEY") +
                        "&features=breezometer_aqi,local_aqi,pollutants_concentrations,pollutants_aqi_information").request(MediaType.APPLICATION_JSON).get(JSONObject.class);
    }
}