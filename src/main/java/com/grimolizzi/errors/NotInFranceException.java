package com.grimolizzi.errors;

public class NotInFranceException extends RuntimeException {

    public NotInFranceException() {
        super("You can't register if you don't live in France!");
    }
}
