package com.example.habittracker.DTOs;

import com.example.habittracker.entities.Habit;
import lombok.Data;

import java.util.List;

@Data

public class UserDTO {
    private Long id;
    private String name;
    private List<HabitDTO> habits;
}
