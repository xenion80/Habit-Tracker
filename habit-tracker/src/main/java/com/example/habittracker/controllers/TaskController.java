package com.example.habittracker.controllers;

import com.example.habittracker.DTOs.TaskDTO;
import com.example.habittracker.inputmodels.CreateTaskRequest;
import com.example.habittracker.inputmodels.RenameTaskRequest;
import com.example.habittracker.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/users/{userId}/categories/{taskId}/complete")
    public ResponseEntity<TaskDTO> completeTask(
            @PathVariable Long taskId,
            @PathVariable Long userId
    ){
        return ResponseEntity.ok(taskService.getTaskDone(taskId,userId));
    }
    @PostMapping("users/{userId}/categories/{categoryId}/tasks")
    public ResponseEntity<TaskDTO> createTask(
            @PathVariable Long categoryId,
            @PathVariable Long userId,
            @RequestBody @Valid CreateTaskRequest request
    ){
        TaskDTO dto= taskService.createTask(categoryId,userId,request.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("users/{userId}/tasks/{taskId}")
    public ResponseEntity<TaskDTO> getTask(
            @PathVariable Long taskId,
            @PathVariable Long userId
    ){
        return ResponseEntity.ok(taskService.getTask(taskId,userId));
    }

    @DeleteMapping("users/{userId}/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long taskId,
            @PathVariable Long userId
    ){
        taskService.deleteTask(taskId,userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/users/{userId}/task/{taskId}")
    public ResponseEntity<TaskDTO> renameTask(
            @PathVariable Long taskId,
            @PathVariable Long userId,
            @Valid@PathVariable RenameTaskRequest request
            ){
        return ResponseEntity.ok(taskService.renameTask(taskId,userId,request));
    }



}
