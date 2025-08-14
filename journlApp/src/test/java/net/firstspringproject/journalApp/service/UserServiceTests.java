package net.firstspringproject.journalApp.service;


import net.firstspringproject.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserServiceTests {
    @Autowired
    UserRepository userRepository;

    @Test
    public void findByUserNameTest(){
        assertNotNull(userRepository.findByUsername("Ronak"));
    }

    @ParameterizedTest
    @CsvSource({
            "1,2,2",
            "4,4,8",
            "5,7,12"
    })
    public void test(int a , int b, int expected){
        assertEquals(expected,a+b);
    }

}
