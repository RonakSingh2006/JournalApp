package net.firstspringproject.journalApp.service;

import net.firstspringproject.journalApp.Service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    EmailService emailService;

    @Test
    public void testEmail(){
        emailService.sendMail("vinodsingh01061972@gmail.com","Testing Email In Java","Hello How Are You");
    }


}
