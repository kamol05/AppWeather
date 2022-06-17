package com.example.appweather.controller;

import com.example.appweather.response.BaseResponse;
import com.example.appweather.service.BaseService;
import com.example.appweather.service.OpenWeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OpenWeatherController {

    private OpenWeatherService weatherService;

    public OpenWeatherController(OpenWeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping(value = "/weather",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getApi(){

        BaseResponse response = weatherService.getWeatherForUser();
        return ResponseEntity.status(response.isSuccess() ? 200 : response.getStatusCode()).body(response.getData());
    }
}
