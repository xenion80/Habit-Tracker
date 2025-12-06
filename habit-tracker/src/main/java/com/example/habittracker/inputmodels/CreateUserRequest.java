package com.example.habittracker.inputmodels;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserRequest {
    @NotBlank(message = "user cannot be empty")
    private String name;
}
