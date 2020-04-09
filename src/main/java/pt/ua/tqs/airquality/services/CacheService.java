package pt.ua.tqs.airquality.services;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import pt.ua.tqs.airquality.cache.Cache;

@Service
public class CacheService {
    private Cache cache;

    private final BreezoMeterService breezometerService;
    private final IPMAService ipmaService;
    private static final String FORECAST = "FORECAST";

    public CacheService(BreezoMeterService breezometerService, IPMAService ipmaService){
        this.cache = new Cache(20,20,15);
        this.breezometerService = breezometerService;
        this.ipmaService = ipmaService;
    }

    public String getAirConditions(String latitude, String longitude, String city) {
        if (!cache.containsKey(city)){
            cache.put(city, breezometerService.getAirConditions(latitude, longitude));
        }
        return cache.get(city).toString();
    }

    public String getForecast(String city){
        if (!cache.containsKey(city+ FORECAST)){
            JSONObject json = ipmaService.getForecast(city);
            if ( json == null){
                return null;
            } else {
                cache.put(city + FORECAST, json);
            }
        }
        return cache.get(city+ FORECAST).toString();
    }

}
