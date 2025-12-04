package com.example.habittracker.controllers;

import com.example.habittracker.DTOs.HabitDTO;
import com.example.habittracker.services.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/habits")
public class HabitController {

    private final HabitService habitService;

    @PostMapping("/{habitId}/complete")
    public ResponseEntity<HabitDTO> completedTask(@PathVariable("habitId") Long id){
        HabitDTO dto=habitService.completeHabit(id);
        return ResponseEntity.ok(dto);
    }
}
