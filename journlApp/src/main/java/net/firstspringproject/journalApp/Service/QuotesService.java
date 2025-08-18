package net.firstspringproject.journalApp.Service;


import net.firstspringproject.journalApp.api.response.QuoteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuotesService {
    private  static final String apiKey = "imJ0Vd51NRccrlO6kK4p6Q==w0MUnUEAm6Ed9enG";

    private static final String API = "https://api.api-ninjas.com/v1/quotes";

    @Autowired
    RestTemplate restTemplate;

    public QuoteResponse getQuote(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key",apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<QuoteResponse[]> response = restTemplate.exchange(API, HttpMethod.GET, entity, QuoteResponse[].class);

        QuoteResponse[] quotes = response.getBody();
        return (quotes != null && quotes.length > 0) ? quotes[0] : null;
    }

}
