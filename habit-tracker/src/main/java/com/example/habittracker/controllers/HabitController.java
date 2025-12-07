package com.example.habittracker.controllers;

import com.example.habittracker.DTOs.HabitDTO;
import com.example.habittracker.DTOs.HabitLogDTO;
import com.example.habittracker.services.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/{habitId}/logs")
    public ResponseEntity<List<HabitLogDTO>> getLogs(@PathVariable Long habitId){
        List<HabitLogDTO> dto=habitService.getLogs(habitId);
        return ResponseEntity.ok(dto);


    }
}
