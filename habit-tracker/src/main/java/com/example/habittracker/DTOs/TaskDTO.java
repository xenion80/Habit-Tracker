package com.example.habittracker.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TaskDTO {
    private Long id;

    private String title;
    private boolean completed;
}
