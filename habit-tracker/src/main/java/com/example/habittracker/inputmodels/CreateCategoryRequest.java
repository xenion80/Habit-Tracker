package com.example.habittracker.inputmodels;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryRequest {

    @NotBlank(message = "Category cannot be empty")
    private String title;
}
