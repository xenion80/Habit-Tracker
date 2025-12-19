package com.example.habittracker.exception;

public class HabitAlreadyCompletedException extends IllegalStateException {
    public HabitAlreadyCompletedException(String message) {
        super(message);
    }
}
