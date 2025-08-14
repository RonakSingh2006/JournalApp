package net.firstspringproject.journalApp.service;

import net.firstspringproject.journalApp.Service.UserDetailsServiceImpl;
import net.firstspringproject.journalApp.repository.UserRepository;
import org.bson.assertions.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.when;


public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void loadUserByUsername(){
        when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn((net.firstspringproject.journalApp.entity.User) User.builder().username("Ronak").password("2006").roles(String.valueOf(new ArrayList<>())).build());
        UserDetails userDetails = userDetailsService.loadUserByUsername("Ronak");
        Assertions.assertNotNull(userDetails);
    }

}
