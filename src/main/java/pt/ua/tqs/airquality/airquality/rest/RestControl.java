package pt.ua.tqs.airquality.airquality.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class RestControl {

    @GetMapping(value = "/ola")
    public List fucn(){
        return null;
    }
}
