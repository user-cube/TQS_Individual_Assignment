package pt.ua.tqs.airquality.services;

import org.springframework.stereotype.Service;
import pt.ua.tqs.airquality.cache.Cache;

@Service
public class CacheService {
    private Cache cache;
    public CacheService(){
        this.cache = new Cache(15, 15, 15);
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

}
