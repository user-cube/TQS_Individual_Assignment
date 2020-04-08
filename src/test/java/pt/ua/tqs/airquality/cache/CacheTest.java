package pt.ua.tqs.airquality.cache;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.concurrent.TimeUnit;

import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CacheTest {

    private Cache cache;

    private String processJson(){
        JSONObject jsonObject;
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/test/java/pt/ua/tqs/airquality/cache/response.json"));
            jsonObject = (JSONObject) obj;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return jsonObject.toString();
    }

    @BeforeEach
    public void setUp() {
        cache = new Cache(10, 10, 2);
    }

    @Test
    /**
     * If we add a value to our cache we should have
     * our cache with a size of one and the key
     * we added should appear in keys.
     */
    public void addToCacheTest() {
        String json = processJson();
        cache.put("AVEIRO", json);
        assertTrue(cache.containsKey("AVEIRO"));
        assertEquals(1, cache.size());
    }

    @Test
    /**
     * If the cache is already full the initial
     * items should be removed and the new ones
     * added to the cache.
     *
     * If the cache have a size of 2 if we insert
     * 3 elements the first one should be removed
     * and the third one added.
     */
    public void addToFullCacheTest(){
        String json = processJson();
        cache.put("AVEIRO", json);
        cache.put("BEJA", json);
        cache.put("BRAGA", json);

        assertEquals(2, cache.size());
        assertTrue(cache.containsKey("BEJA"));
        assertTrue(cache.containsKey("BRAGA"));

        assertFalse(cache.containsKey("AVEIRO"));

    }

    @Test
    /**
     * When getting an element that exists in
     * cache we should have the exact same value
     * that we want.
     */
    public void getAnExistingElementTest() {
        String json = processJson();
        cache.put("AVEIRO", json);
        assertEquals(json, cache.get("AVEIRO"));
    }

    @Test
    /**
     * When getting an element that does not
     * exists in cache we should get a null
     * reference.
     */
    public void getAnNonExistingElementTest(){
        assertEquals(null, cache.get("BEJA"));
    }

    @Test
    /**
     * When removing an element that exists in cache
     * and the cache was only one element we should
     * get a size of 0.
     */
    public void removeAnExistingElementTest() {
        String json = processJson();
        cache.put("AVEIRO", json);
        assertEquals(1, cache.size());
        cache.remove("AVEIRO");
        assertEquals(0, cache.size());
    }

    @Test
    /**
     * When testing the actual size of
     * out cache it should return the
     * number of elements.
     */
    public void cacheSizeTest() {
        assertEquals(0, cache.size());
        String json = processJson();
        cache.put("AVEIRO", json);
        cache.put("BEJA", json);

        assertEquals(2, cache.size());
    }

    @Test
    /**
     * If the key does not exist we should
     * receive a false and a true if the
     * key exists.
     */
    public void containsKeyTest() {
        assertFalse(cache.containsKey("AVEIRO"));
        String json = processJson();
        cache.put("AVEIRO", json);
        assertTrue(cache.containsKey("AVEIRO"));
    }

    @Test
    /**
     * If we invoke the method we
     * should clean our cache.
     */
    public void cleanupCacheTest() throws InterruptedException {
        cache.put("AVEIRO", processJson());
        TimeUnit.MILLISECONDS.sleep(20000);
        cache.cleanup();
        TimeUnit.MILLISECONDS.sleep(20000);
        assertFalse(cache.containsKey("AVEIRO"));
        assertEquals(0, cache.size());
    }
}