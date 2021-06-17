package com.grimolizzi.user;

import com.grimolizzi.errors.NotAdultException;
import com.grimolizzi.errors.NotInFranceException;
import com.grimolizzi.errors.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public Iterable<User> find(String lastName) {
        if (lastName != null) {
            return this.repository.findByLastName(lastName);
        }
        return this.repository.findAll();
    }

    public void save(User user) {

        if (user.getDateOfBirth() == null || (user.getDateOfBirth() != null && !isAdult(user.getDateOfBirth()))) {
            throw new NotAdultException();
        }

        if (!user.getNation().equalsIgnoreCase("France")) {
            throw new NotInFranceException();
        }

        this.repository.save(user);
    }

    private boolean isAdult(LocalDate dateOfBirth) {
        LocalDate now = LocalDate.now();
        Period period = Period.between(dateOfBirth, now);
        return period.getYears() >= 18;
    }

    public User findById(String id) {
        return this.repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
