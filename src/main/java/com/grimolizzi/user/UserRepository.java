package com.grimolizzi.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    public List<User> findByLastName(String lastName);
    public Optional<User> findById(String id);
}
