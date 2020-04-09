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
import pt.ua.tqs.airquality.tools.ProcessJSON;

@RestController
@RequestMapping("/forecast")
public class ForecastController {

    private final CacheService cacheService;
    private ProcessJSON processJSON;

    public ForecastController(CacheService cacheService, ProcessJSON processJSON) {
        this.cacheService = cacheService;
        this.processJSON = processJSON;
    }

    @ApiOperation(value = "Get weather forecast for the next five day by city.", response = Iterable.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
    @GetMapping(value = "/{city}")
    public ResponseEntity getAirConditions(@PathVariable("city") String city) {
        JSONObject json;
        String data = cacheService.getForecast(city);
        if (data == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        json = processJSON.processJSON(data);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }
}
