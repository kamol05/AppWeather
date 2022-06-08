package com.example.appweather.controller;

import com.example.appweather.service.OpenWeatherService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenWeatherController {

    private OpenWeatherService weatherService;

    public OpenWeatherController(OpenWeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping(value = "/weather",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getApi(){
        return weatherService.getWeatherForUser();
    }
}
