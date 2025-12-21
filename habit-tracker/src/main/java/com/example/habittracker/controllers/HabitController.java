package com.example.habittracker.controllers;

import com.example.habittracker.DTOs.HabitDTO;
import com.example.habittracker.DTOs.HabitLogDTO;
import com.example.habittracker.entities.Habit;
import com.example.habittracker.inputmodels.CreateHabitRequest;
import com.example.habittracker.services.HabitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor

public class HabitController {

    private final HabitService habitService;


    @PostMapping("/users/{id}/habits")
    public ResponseEntity<HabitDTO> createHabitForUser(
            @PathVariable Long id,
            @Valid @RequestBody CreateHabitRequest request
    ){
        HabitDTO dto=habitService.createHabit(id,request.getTitle());
        return ResponseEntity.status((HttpStatus.CREATED)).body(dto);

    }

    @PostMapping("/users/{userId}/habits/{habitId}/complete")
    public ResponseEntity<HabitDTO> completedHabit(
            @PathVariable Long habitId,
            @PathVariable Long userId
    ) {
        HabitDTO dto = habitService.completeHabit(habitId, userId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/users/{userId}/habits/{habitId}/logs")
    public ResponseEntity<Page<HabitLogDTO>> getLogs(
            @PathVariable Long habitId,
            @PathVariable Long userId,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date,desc") String sort
    ) {
        String[] sortParams = sort.split(",");
        Sort sorting = sortParams[1].equalsIgnoreCase("desc")
                ? Sort.by(sortParams[0]).descending()
                : Sort.by(sortParams[0]).ascending();

        Pageable pageable = PageRequest.of(page, size, sorting);


        LocalDate start = from != null ? LocalDate.parse(from) : null;
        LocalDate end = to != null ? LocalDate.parse(to) : null;

        return ResponseEntity.ok(
                habitService.getLogs(habitId,userId, start, end, pageable)
        );
    }
    @GetMapping("/users/{id}/habits")
    public ResponseEntity<Page<HabitDTO>> getHabitForUser(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String sort
    ) {
        String[] sortParams = sort.split(",");
        Sort sorting = sortParams[1].equalsIgnoreCase("desc")
                ? Sort.by(sortParams[0]).descending()
                : Sort.by(sortParams[0]).ascending();

        Pageable pageable = PageRequest.of(page, size, sorting);

        return ResponseEntity.ok(habitService.getAllHabitForUser(id, pageable));
    }
    @GetMapping("/users/{userId}/habits/{habitId}")
    public ResponseEntity<HabitDTO> getHabitById(
            @PathVariable Long habitId,
            @PathVariable Long userId
            ){
        HabitDTO dto=habitService.getHabitById(habitId,userId);
        return ResponseEntity.status(HttpStatus.FOUND).body(dto);
    }

    @DeleteMapping("users/{userId}/habits/{habitId}")
    public ResponseEntity<Void> deleteHabit(
            @PathVariable Long habitId,
            @PathVariable Long userId
    ){
        habitService.deleteHabit(habitId,userId);
        return ResponseEntity.noContent().build();
    }



}
