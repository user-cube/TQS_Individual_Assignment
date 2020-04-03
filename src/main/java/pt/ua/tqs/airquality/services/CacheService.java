package pt.ua.tqs.airquality.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.tqs.airquality.cache.Cache;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class CacheService {
    private Cache cache;

    @Autowired
    BreezometerService breezometerService;

    public CacheService(){
        this.cache = new Cache(15,15,15);
    }

    public Cache getCache(){
        return this.cache;
    }

    public LinkedHashMap getAirConditions(String latitude, String longitude, String city) {
        LinkedHashMap cached;
        LinkedHashMap toCache;

        System.out.println(this.cache.get(city));

        if (this.cache.containsKey(city)){
            try{
                cached = (LinkedHashMap) this.cache.get(city);
            } catch (Exception e){
                toCache = breezometerService.getAirConditions(latitude, longitude);
                this.cache.put(city, toCache);
                return toCache;
            }
        } else {
            toCache = breezometerService.getAirConditions(latitude, longitude);
            this.cache.put(city, toCache);
            return toCache;
        }
        return cached;
    }

}
