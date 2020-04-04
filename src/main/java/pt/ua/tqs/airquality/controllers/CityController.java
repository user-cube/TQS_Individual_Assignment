package pt.ua.tqs.airquality.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.tqs.airquality.entities.CitiesCoordinates;
import pt.ua.tqs.airquality.entities.CitiesNames;
import pt.ua.tqs.airquality.services.CacheService;

import java.util.*;


@RestController
@RequestMapping("/cities")
public class CityController {

    private final CacheService cacheService;

    public CityController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @ApiOperation(value = "List all available cities.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/")
    public ResponseEntity listAllCities(){
        List cities = new ArrayList();
        for (CitiesNames city: CitiesNames.values()) {
            cities.add(city.toString());
        }
        return ResponseEntity.status(HttpStatus.OK).body(cities);
    }

    @GetMapping(value="/{city}")
    public ResponseEntity getAirConditions(@PathVariable("city") String city){
        String cityName = city.toUpperCase().replaceAll("\\s", "");
        String latitude = CitiesCoordinates.valueOf(cityName + "_LAT").toString();
        String longitude = CitiesCoordinates.valueOf(cityName + "_LONG").toString();
        JSONObject json = null;
        String data = cacheService.getAirConditions(latitude, longitude, city);
        try {
            json = (JSONObject) new JSONParser().parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }
}
