package net.firstspringproject.journalApp.Service;


import net.firstspringproject.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    private static final String apiKey = "b3427f2de3c46176db58c25a8e8f8419";
    private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";


    @Autowired
    RestTemplate restTemplate;

    public WeatherResponse getWeather(String city){
        String finalApi = API.replace("API_KEY",apiKey).replace("CITY",city);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }

}
