package com.example.habittracker.controllers;

import com.example.habittracker.DTOs.HabitDTO;
import com.example.habittracker.DTOs.UserDTO;
import com.example.habittracker.inputmodels.CreateHabitRequest;
import com.example.habittracker.inputmodels.CreateUserRequest;
import com.example.habittracker.services.HabitService;
import com.example.habittracker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final HabitService habitService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserRequest request){
        UserDTO dto=userService.createUser(request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @PostMapping("/{id}/habits")
    public ResponseEntity<HabitDTO> createHabitForUser(
            @PathVariable Long id,
            @RequestBody CreateHabitRequest request
    ){
        HabitDTO dto=habitService.createHabit(id,request.getTitle());
        return ResponseEntity.status((HttpStatus.CREATED)).body(dto);

    }

    @GetMapping("/{id}/habits")
    public ResponseEntity<List<HabitDTO>> getHabitForUser(@PathVariable Long id){
        List<HabitDTO> dtos=habitService.getAllHabitForUser(id);
        return ResponseEntity.ok(dtos);
    }


}
