package com.grimolizzi.errors;

public class NotAdultException extends RuntimeException {

    public NotAdultException() {
        super("You can't register if you're not 18 years old!");
    }
}