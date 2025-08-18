package net.firstspringproject.journalApp.Service;


import net.firstspringproject.journalApp.api.response.WeatherResponse;
import net.firstspringproject.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;


    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AppCache appCache;

    public WeatherResponse getWeather(String city){
        String finalApi = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace("<apikey>",apiKey).replace("<city>",city);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }

}
