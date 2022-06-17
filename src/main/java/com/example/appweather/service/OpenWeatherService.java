package com.example.appweather.service;


import com.example.appweather.model.Country;
import com.example.appweather.model.User;
import com.example.appweather.repository.UserRepository;
import com.example.appweather.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class OpenWeatherService implements BaseService {

    private UserRepository userRepository;

    public OpenWeatherService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());

    @Value("${weather.api.urlWeather}")
    private String url;

    @Value("${weather.api.key}")
    private String apiKey;

    public BaseResponse getWeatherForUser() {
        Country country = findCountryForUser();
        String readyUrl = urlResolver(country.getLatitude(), country.getLongitude(), apiKey);

        ResponseEntity<String> response = restTemplate.getForEntity(readyUrl, String.class);
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            log.info("Get Data from Api Successfull");
            return SPECIAL(response.getBody(), "Get Data from Api Successfull", 200, true);
        }
        log.warn("Get Data from Api Failed");
        return SPECIAL(response.getBody(), "Get Data from Api Failed", response.getStatusCode().value(), false);
    }

    private String urlResolver(double latitude, double longitude, String apiKey){
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append("lat=");
        sb.append(latitude);
        sb.append("&");
        sb.append("lon=");
        sb.append(longitude);
        sb.append("&");
        sb.append("appid=");
        sb.append(apiKey);
        return sb.toString();
    }

    private Country findCountryForUser(){
        User user = userRepository.findByLogin(UserUtil.getLoginFromAuthentication()).get();
        return user.getCountry();
    }


    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 5000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }





}
