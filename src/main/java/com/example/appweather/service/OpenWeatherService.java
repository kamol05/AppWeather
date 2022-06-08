package com.example.appweather.service;


import com.example.appweather.model.Country;
import com.example.appweather.model.User;
import com.example.appweather.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenWeatherService {

    private UserRepository userRepository;

    public OpenWeatherService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());

    private final String url = "https://api.openweathermap.org/data/2.5/weather?";

    private final String apiKey = "api";

    public ResponseEntity<?> getWeatherForUser() {
        Country country = findCountryForUser();
        String readyUrl = urlResolver(country.getLatitude(), country.getLongitude(), apiKey);

        ResponseEntity<String> response = restTemplate.getForEntity(readyUrl, String.class);
        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
        return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
    }

    public String urlResolver(double latitude, double longitude, String apiKey){
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

    public Country findCountryForUser(){
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


    //    private String url4 = "https://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid={}";
    //    private String url4 = "https://api.openweathermap.org/data/2.5/weather?lat=41.311081&lon=69.240562&appid={api}";



}
