package pt.ua.tqs.airquality.cache;

public class CacheObject {
    private Long lastAccess = System.currentTimeMillis();
    private String value;

    public CacheObject(String value) {
        this.value = value;
    }

    public void setLastAccess(Long lastAccess) {
        this.lastAccess = lastAccess;
    }

    public String getValue() {
        return value;
    }

    public Long getLastAccess() {
        return lastAccess;
    }
}
