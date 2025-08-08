package net.firstspringproject.journalApp.Controller;

import net.firstspringproject.journalApp.Service.UserService;
import net.firstspringproject.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        return new ResponseEntity<>(userService.getAll(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        userService.saveEntry(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/username/{username}")
    public ResponseEntity<?> update(@RequestBody User user , @PathVariable String username){
        User userInDb = userService.findByUserName(user.getUsername());
        if(userInDb != null){
            userInDb.setUsername(username);
            userInDb.setPassword(user.getPassword());

            userService.saveEntry(userInDb);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
