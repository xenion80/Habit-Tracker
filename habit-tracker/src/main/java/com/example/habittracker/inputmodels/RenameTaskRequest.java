package com.example.habittracker.inputmodels;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RenameTaskRequest {
    @NotBlank
    private String title;
}
