package com.example.habittracker.DTOs;

import lombok.Data;

@Data
public class HabitDTO {
    private Long id;
    private String title;
    private int streak;
}
