package pt.ua.tqs.airquality.cache;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.map.LRUMap;
import pt.ua.tqs.airquality.services.BreezoMeterService;

public class Cache<K, T> {

    private long timeToLive;
    private final LRUMap cacheMap;

    public Cache(long cacheTimeToLive, final long timerInterval, int maxItems) {
        this.timeToLive = cacheTimeToLive * 1000;

        cacheMap = new LRUMap(maxItems);

        if (timeToLive > 0 && timerInterval > 0) {

            Thread t = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(timerInterval * 1000);
                    } catch (InterruptedException exception) {
                        Logger.getLogger(BreezoMeterService.class.getName()).log(Level.SEVERE, null, exception);
                    }
                    cleanup();
                }
            });
            t.setDaemon(true);
            t.start();
        }
    }

    public void put(K key, T value) {
        synchronized (cacheMap) {
            cacheMap.put(key, new CacheObject(value));
        }
    }

    public T get(K key) {
        synchronized (cacheMap) {
            CacheObject cacheObject = (CacheObject) cacheMap.get(key);

            if (cacheObject == null)
                return null;
            else {
                cacheObject.lastAccessed = System.currentTimeMillis();
                return cacheObject.value;
            }
        }
    }

    public void remove(K key) {
        synchronized (cacheMap) {
            cacheMap.remove(key);
        }
    }

    public int size() {
        synchronized (cacheMap) {
            return cacheMap.size();
        }
    }

    public boolean containsKey(T key) {
        for(Object k : this.cacheMap.keySet()) {
            if (k.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public void cleanup() {

        long now = System.currentTimeMillis();
        ArrayList<K> deleteKey;

        synchronized (cacheMap) {
            MapIterator itr = cacheMap.mapIterator();

            deleteKey = new ArrayList<>((cacheMap.size() / 2) + 1);
            K key;
            CacheObject cacheObject;

            while (itr.hasNext()) {
                key = (K) itr.next();
                cacheObject = (CacheObject) itr.getValue();

                if (cacheObject != null && (now > (timeToLive + cacheObject.lastAccessed))) {
                    deleteKey.add(key);
                }
            }
        }

        for (K key : deleteKey) {
            synchronized (cacheMap) {
                cacheMap.remove(key);
            }
            Thread.yield();
        }
    }

    protected class CacheObject {
        public long lastAccessed = System.currentTimeMillis();
        public T value;
        protected CacheObject(T value) {
            this.value = value;
        }
    }
}