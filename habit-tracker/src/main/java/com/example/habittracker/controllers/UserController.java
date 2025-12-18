package com.example.habittracker.controllers;

import com.example.habittracker.DTOs.HabitDTO;
import com.example.habittracker.DTOs.TaskCategoryDTO;
import com.example.habittracker.DTOs.UserDTO;
import com.example.habittracker.inputmodels.CreateCategoryRequest;
import com.example.habittracker.inputmodels.CreateHabitRequest;
import com.example.habittracker.inputmodels.CreateUserRequest;
import com.example.habittracker.services.CategoryService;
import com.example.habittracker.services.HabitService;
import com.example.habittracker.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final HabitService habitService;
    private final UserService userService;
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserRequest request){
        UserDTO dto=userService.createUser(request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }





    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserwithhabits(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserWithHabits(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }


}
