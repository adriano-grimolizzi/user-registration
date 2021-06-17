package com.grimolizzi.errors;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String id) {
        super("User with id: " + id + " not found.");
    }
}