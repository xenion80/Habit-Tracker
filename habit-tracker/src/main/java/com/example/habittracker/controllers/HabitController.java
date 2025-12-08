package com.example.habittracker.controllers;

import com.example.habittracker.DTOs.HabitDTO;
import com.example.habittracker.DTOs.HabitLogDTO;
import com.example.habittracker.services.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<Page<HabitLogDTO>> getLogs(
            @PathVariable Long habitId,
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
                habitService.getLogs(habitId, start, end, pageable)
        );
    }

}
