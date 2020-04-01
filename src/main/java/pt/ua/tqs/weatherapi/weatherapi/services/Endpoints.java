package pt.ua.tqs.weatherapi.weatherapi.services;

public enum Endpoints {
    BASEURL("http://api.ipma.pt/open-data/"),
    WEATHER("http://api.ipma.pt/open-data/forecast/meteorology/cities/daily/"),
    WEATHERTYPE("http://api.ipma.pt/open-data/weather-type-classe"),
    CODDE("http://api.ipma.pt/open-data/distrits-islands");

    public final String label;

    Endpoints(String label) {
        this.label = label;
    }
}