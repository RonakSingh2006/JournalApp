package net.firstspringproject.journalApp.Controller;

import net.firstspringproject.journalApp.Service.UserService;
import net.firstspringproject.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;

    @GetMapping("/health-check")
    public String check() {
        return "OK";
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user){
        userService.saveEntry(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
