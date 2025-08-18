package net.firstspringproject.journalApp.Service;


import net.firstspringproject.journalApp.api.response.QuoteResponse;
import net.firstspringproject.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuotesService {

    @Value("${quotes.api.key}")
    private String apiKey;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AppCache appCache;

    public QuoteResponse getQuote(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key",apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<QuoteResponse[]> response = restTemplate.exchange(appCache.appCache.get(AppCache.keys.QUOTES_API.toString()), HttpMethod.GET, entity, QuoteResponse[].class);

        QuoteResponse[] quotes = response.getBody();
        return (quotes != null && quotes.length > 0) ? quotes[0] : null;
    }

}
