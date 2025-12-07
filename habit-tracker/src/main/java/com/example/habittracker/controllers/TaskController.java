package com.example.habittracker.controllers;

import com.example.habittracker.DTOs.TaskDTO;
import com.example.habittracker.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/{taskId}/complete")
    public ResponseEntity<TaskDTO> completeTask(@PathVariable Long taskId){
        return ResponseEntity.ok(taskService.getTaskDone(taskId));
    }


}
