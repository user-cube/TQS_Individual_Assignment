package pt.ua.tqs.airquality.cache;

import java.util.HashMap;

public class Cache<K, T> {

    private HashMap<K, T> cacheMap;

    public Cache(long timeToLive, final long timeInterval, int max) {
        cacheMap = new HashMap<>(max);
        if (timeToLive > 0 && timeInterval > 0) {
            Thread t = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(timeInterval * 1000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }

                }
            });
            t.setDaemon(true);
            t.start();
        }
    }

    public void put(K key, T value) {
        synchronized (cacheMap) {
            cacheMap.put(key, value);
        }
    }

    public T get(K key) {
        synchronized (cacheMap) {
            CacheObject cacheObject = new CacheObject(cacheMap.get(key).toString());

            if (cacheObject.getValue() != null) {
                cacheObject.setLastAccess(System.currentTimeMillis());
                return (T) cacheObject.getValue();
            }
            return null;
        }
    }

    public boolean containsKey(T key) {
        for (K k : this.cacheMap.keySet()) {
            if (k.equals(key)) {
                return true;
            }
        }
        return false;
    }
}
