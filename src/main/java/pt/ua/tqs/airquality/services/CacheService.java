package pt.ua.tqs.airquality.services;

import org.springframework.stereotype.Service;
import pt.ua.tqs.airquality.cache.Cache;

@Service
public class CacheService {
    private Cache cache;

    final BreezometerService breezometerService;

    public CacheService(BreezometerService breezometerService){
        this.cache = new Cache(15,15,15);
        this.breezometerService = breezometerService;
    }

    public String getAirConditions(String latitude, String longitude, String city) {
        if (!cache.containsKey(city)){
            cache.put(city, breezometerService.getAirConditions(latitude, longitude));
        }
        return cache.get(city).toString();
    }

}
