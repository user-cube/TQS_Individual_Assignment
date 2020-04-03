package pt.ua.tqs.airquality.services;

import pt.ua.tqs.airquality.entities.CitiesCoordinates;

public class CoordinatesService {
    private String latitude;
    private String longitude;

    public CoordinatesService(String city) {
        this.latitude = CitiesCoordinates.valueOf(city).toString();
        this.longitude = CitiesCoordinates.valueOf(city).toString();
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
