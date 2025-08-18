package net.firstspringproject.journalApp.Service;

import lombok.extern.slf4j.Slf4j;
import net.firstspringproject.journalApp.entity.User;
import net.firstspringproject.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // save entry
    public boolean saveNewUser(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            userRepository.save(user);
            return true;
        }
        catch (Exception e){
            log.error("User already Present",e);
            return false;
        }
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER","ADMIN"));
        userRepository.save(user);
    }


    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> getById(ObjectId id){
        return userRepository.findById(id);
    }

    public void delete(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUserName(String username){
        return userRepository.findByUsername(username);
    }

    public void deleteByUserName(String username){
        userRepository.deleteByUsername(username);
    }

}
