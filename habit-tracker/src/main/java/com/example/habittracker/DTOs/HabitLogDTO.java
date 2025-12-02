package com.example.habittracker.DTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HabitLogDTO {
    private Long id;

    private LocalDate date;
}
