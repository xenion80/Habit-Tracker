package com.example.habittracker.controllers;

import com.example.habittracker.DTOs.HabitDTO;
import com.example.habittracker.DTOs.HabitLogDTO;
import com.example.habittracker.entities.Habit;
import com.example.habittracker.inputmodels.CreateHabitRequest;
import com.example.habittracker.inputmodels.RenameHabitRequest;
import com.example.habittracker.services.HabitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
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
            @PathVariable Long userId,
            @PathVariable Long habitId,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate from,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate to,

            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "date",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                habitService.getLogs(userId, habitId, from, to, pageable)
        );
    }

    @GetMapping("/users/{id}/habits")
    public ResponseEntity<Page<HabitDTO>> getHabitForUser(
            @PathVariable Long id,
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {

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

    @PatchMapping("/users/{userId}/habits/{habitId}")
    public ResponseEntity<HabitDTO> renameHabit(
            @PathVariable Long habitId,
            @PathVariable Long userId,
            @Valid@RequestBody RenameHabitRequest request
            ){
        HabitDTO dto=habitService.renameHabit(habitId,userId,request);
        return ResponseEntity.ok(dto);
    }



}
