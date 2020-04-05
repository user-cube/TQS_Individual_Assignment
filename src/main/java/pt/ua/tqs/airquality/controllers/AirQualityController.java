package pt.ua.tqs.airquality.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.tqs.airquality.services.CacheService;
import pt.ua.tqs.airquality.tools.GetCity;
import pt.ua.tqs.airquality.tools.ProcessJSON;


@RestController
@RequestMapping("/airquality")
public class AirQualityController {

    private final CacheService cacheService;
    private GetCity getCity;
    private JSONObject json;
    private ProcessJSON processJSON;

    public AirQualityController(CacheService cacheService, GetCity getCity, ProcessJSON processJSON) {
        this.cacheService = cacheService;
        this.getCity = getCity;
        this.processJSON = processJSON;
    }

    @ApiOperation(value = "Get air quality by city.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value="/{city}")
    public ResponseEntity getAirConditions(@PathVariable("city") String city){
        String [] coordinates = getCity.coordinatesFromName(city);

        if ( coordinates == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        String data = cacheService.getAirConditions(coordinates[0], coordinates[1], city);
        json = processJSON.processJSON(data);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

}
