package pt.ua.tqs.airquality.tools;

import org.springframework.stereotype.Service;
import pt.ua.tqs.airquality.entities.CitiesCoordinates;
import pt.ua.tqs.airquality.services.BreezometerService;

import java.security.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class GetCity {

    public String [] coordinatesFromName(String cityName){
        String [] coordinates = new String[2];

        try {
            coordinates[0] = CitiesCoordinates.valueOf(cityName + "_LAT").toString();
            coordinates[1] = CitiesCoordinates.valueOf(cityName + "_LONG").toString();
        } catch (Exception exception){
            System.err.println(exception);
            Logger.getLogger(BreezometerService.class.getName()).log(Level.SEVERE, null, exception);
            return null;
        }
        return coordinates;
    }
}
