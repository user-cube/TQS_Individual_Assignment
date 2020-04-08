package pt.ua.tqs.airquality.tools;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import pt.ua.tqs.airquality.entities.CitiesCoordinates;
import pt.ua.tqs.airquality.services.BreezoMeterService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CityInfo {

    private final Client client = ClientBuilder.newClient();

    public String [] coordinatesFromName(String city){
        String cityName = city.toUpperCase().replaceAll("\\s", "");
        String [] coordinates = new String[2];

        try {
            coordinates[0] = CitiesCoordinates.valueOf(cityName + "_LAT").toString();
            coordinates[1] = CitiesCoordinates.valueOf(cityName + "_LONG").toString();
        } catch (Exception exception){
            Logger.getLogger(BreezoMeterService.class.getName()).log(Level.SEVERE, null, exception);
            return null;
        }
        return coordinates;
    }
    public String getCityCode(String city, String cityUrl){
        JSONObject data = client
                .target(cityUrl)
                .request(MediaType.APPLICATION_JSON)
                .get(JSONObject.class);
        List codes = (List) data.get("data");
        for (int i = 0; i< codes.size();i++){
            LinkedHashMap mapa = (LinkedHashMap) codes.get(i);
            if (mapa.get("local").equals(city)){
                return mapa.get("globalIdLocal")+"";
            }

        }
        return null;
    }

}
