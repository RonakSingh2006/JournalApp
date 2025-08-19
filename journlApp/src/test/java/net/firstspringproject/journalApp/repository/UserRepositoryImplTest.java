package net.firstspringproject.journalApp.repository;


import net.firstspringproject.journalApp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserRepositoryImplTest {

    @Autowired
    UserRepositoryImpl userRepositoryimpl;

    @Test
    public void testgetUserSA(){
        List<User> userList = userRepositoryimpl.getUserForSA();
    }


}
