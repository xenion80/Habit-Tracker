package com.example.habittracker.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RootController {
    @GetMapping("/")
    public Map<String, Object> root() {
        return Map.of(
                "service", "Habit Tracker API",
                "status", "UP",
                "version", "v1",
                "docs", "/API.md"
        );
    }
}
