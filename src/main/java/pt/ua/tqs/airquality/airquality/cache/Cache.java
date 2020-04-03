package pt.ua.tqs.airquality.airquality.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Cache<K, T> {

    private Long cacheTimeToLive;
    private String value;
    private CacheObject cacheObject;
    private HashMap<K, T> cacheMap;

    public Cache(long timeToLive, final long timeInterval, int max) {
        this.cacheTimeToLive = timeToLive * 2000;

        cacheMap = new HashMap<K, T>(max);

        if (timeToLive > 0 && timeInterval > 0) {

            Thread t = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(timeInterval * 1000);
                    } catch (InterruptedException ex) {
                        // Restore interrupted state...
                        Thread.currentThread().interrupt();
                    }

                }
            });

            t.setDaemon(true);
            t.start();
        }
    }

    // PUT method
    public void put(K key, T value) {
        synchronized (cacheMap) {
            cacheMap.put(key, value);
        }
    }

    public T get(K key) {
        synchronized (cacheMap) {
            cacheObject = new CacheObject(cacheMap.get(key).toString());

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

    // REMOVE method
    public void remove(String key) {
        synchronized (cacheMap) {
            cacheMap.remove(key);
        }
    }

    // Get Cache Objects Size()
    public int size() {
        synchronized (cacheMap) {
            return cacheMap.size();
        }
    }

    // CLEANUP method
    public void cleanup() {

        long now = System.currentTimeMillis();
        ArrayList<String> deleteKey;

        synchronized (cacheMap) {
            Iterator<?> itr = cacheMap.entrySet().iterator();

            deleteKey = new ArrayList<String>((cacheMap.size() / 2) + 1);

            while (itr.hasNext()) {
                String key = (String) itr.next();
                cacheObject = (CacheObject) ((Entry<?, ?>) itr).getValue();
                if (cacheObject != null && (now > (cacheTimeToLive + cacheObject.getLastAccess()))) {
                    deleteKey.add(key);
                }
            }
        }

        for (String key : deleteKey) {
            synchronized (cacheMap) {
                cacheMap.remove(key);
            }

            Thread.yield();
        }
    }
}
