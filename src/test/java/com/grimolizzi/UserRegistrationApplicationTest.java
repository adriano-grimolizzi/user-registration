package com.grimolizzi;

import com.grimolizzi.user.UserController;
import com.grimolizzi.user.UserRepository;
import com.grimolizzi.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRegistrationApplicationTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService service;

    @Autowired
    private UserController controller;

    @Test
    void contextLoads() {

    }

}