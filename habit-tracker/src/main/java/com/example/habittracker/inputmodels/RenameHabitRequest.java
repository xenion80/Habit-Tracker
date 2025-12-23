package com.example.habittracker.inputmodels;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RenameHabitRequest {
    @NotBlank
    private String title;
}
