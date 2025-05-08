package com.example.exception;

/**
 * Exception thrown when a user is not found
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}

