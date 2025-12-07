package com.example.habittracker.controllers;

import com.example.habittracker.DTOs.TaskDTO;
import com.example.habittracker.inputmodels.CreateTaskRequest;
import com.example.habittracker.services.TaskService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<TaskDTO>> getTasksbyCategory(@PathVariable Long categoryId){
        List<TaskDTO> dto=taskService.getTaskByCategory(categoryId);
        return ResponseEntity.ok(dto);
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
