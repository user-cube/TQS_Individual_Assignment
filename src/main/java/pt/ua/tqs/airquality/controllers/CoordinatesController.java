package pt.ua.tqs.airquality.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.tqs.airquality.entities.CitiesCoordinates;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/coordinates")
public class CoordinatesController {
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @ApiOperation(value = "Get latitude and longitude by city.", response = Iterable.class)
    @GetMapping(value = "/{city}")
    public ResponseEntity coordinatesByCity(@PathVariable("city") String city) {
        Map<String, String> coordinates = new HashMap<>();
        try {
            String cityName = city.toUpperCase().replaceAll("\\s", "");
            String latitude = CitiesCoordinates.valueOf(cityName + "_LAT").toString();
            String longitude = CitiesCoordinates.valueOf(cityName + "_LONG").toString();
            coordinates.put("latitude", latitude);
            coordinates.put("longitude", longitude);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nothing to show.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(coordinates);
    }
}
