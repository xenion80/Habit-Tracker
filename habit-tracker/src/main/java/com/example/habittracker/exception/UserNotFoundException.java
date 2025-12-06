package com.example.habittracker.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String msg) { super(msg); }
}
