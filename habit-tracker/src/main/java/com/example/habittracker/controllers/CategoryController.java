package com.example.habittracker.controllers;

import com.example.habittracker.DTOs.TaskDTO;
import com.example.habittracker.inputmodels.CreateTaskRequest;
import com.example.habittracker.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final TaskService taskService;

    @GetMapping("/{categoryId}/tasks")
    public ResponseEntity<Page<TaskDTO>> getTasksByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id,asc") String sort
    ) {
        String[] sortParams = sort.split(",");
        Sort sorting = sortParams[1].equalsIgnoreCase("desc")
                ? Sort.by(sortParams[0]).descending()
                : Sort.by(sortParams[0]).ascending();

        Pageable pageable = PageRequest.of(page, size, sorting);

        return ResponseEntity.ok(taskService.getTasksByCategory(categoryId, pageable));
    }

    @PostMapping("/{categoryId}/tasks")
    public ResponseEntity<TaskDTO> createTask(
            @PathVariable Long categoryId,
            @RequestBody CreateTaskRequest request
    ){
        TaskDTO dto=taskService.createTask(categoryId,request.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
