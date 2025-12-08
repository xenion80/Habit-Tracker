package com.example.habittracker.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class HabitDTO {
    private Long id;
    private String title;
    private int streak;
}
