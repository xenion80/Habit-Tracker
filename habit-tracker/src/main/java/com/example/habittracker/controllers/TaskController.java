package com.example.habittracker.controllers;

import com.example.habittracker.DTOs.TaskDTO;
import com.example.habittracker.inputmodels.CreateTaskRequest;
import com.example.habittracker.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/categories/{taskId}/complete")
    public ResponseEntity<TaskDTO> completeTask(@PathVariable Long taskId){
        return ResponseEntity.ok(taskService.getTaskDone(taskId));
    }
    @PostMapping("/categories/{categoryId}/tasks")
    public ResponseEntity<TaskDTO> createTask(
            @PathVariable Long categoryId,
            @RequestBody @Valid CreateTaskRequest request
    ){
        TaskDTO dto= taskService.createTask(categoryId,request.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId){
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }



}
