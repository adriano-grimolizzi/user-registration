package com.grimolizzi.user;

import com.grimolizzi.errors.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;

    @GetMapping
    public Iterable<User> find(@RequestParam(required = false) String lastName) {
        logger.info("Retrieving users:");
        Date start = new Date();
        Iterable<User> users = this.service.find(lastName);
        logger.info("Retrieved:" + users.toString());
        logTime(start);
        return users;
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable("id") String id) {

        return this.service.findById(id);
    }

    @PostMapping
    public void save(@Valid @RequestBody User user) {
        logger.info("Saving user:");
        logger.info(user.toString());
        Date start = new Date();
        this.service.save(user);
        logger.info("Saved.");
        logTime(start);
    }

    private void logTime(Date start) {
        Date end = new Date();
        long elapsed = end.getTime() - start.getTime();
        logger.info("Elapsed time: " + elapsed + " ms");
    }
}
