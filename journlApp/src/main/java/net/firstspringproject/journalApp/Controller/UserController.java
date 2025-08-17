package net.firstspringproject.journalApp.Controller;

import net.firstspringproject.journalApp.Service.QuotesService;
import net.firstspringproject.journalApp.Service.UserService;
import net.firstspringproject.journalApp.Service.WeatherService;
import net.firstspringproject.journalApp.api.response.QuoteResponse;
import net.firstspringproject.journalApp.api.response.WeatherResponse;
import net.firstspringproject.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    WeatherService weatherService;

    @Autowired
    QuotesService quotesService;

    @PutMapping
    public ResponseEntity<?> update(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User userInDb = userService.findByUserName(username);
        if(userInDb != null){
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());

            userService.saveNewUser(userInDb);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        userService.deleteByUserName(username);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        WeatherResponse weatherResponse = weatherService.getWeather("MUMBAI");
        String greeting = "";
        if(weatherResponse != null){
            greeting = "\nWeather Feels Like " + weatherResponse.getCurrent().getFeelslike();
        }

        QuoteResponse quoteResponse = quotesService.getQuote();
        String quote = "";
        if(quoteResponse != null){
            quote = "\nTodays Quote : "+quoteResponse.getQuote();
        }
        return new ResponseEntity<>("Hi "+authentication.getName() + greeting + quote, HttpStatus.OK);
    }


}
