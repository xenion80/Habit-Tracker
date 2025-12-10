package com.example.habittracker.controllers;

import com.example.habittracker.DTOs.HabitDTO;
import com.example.habittracker.DTOs.TaskCategoryDTO;
import com.example.habittracker.DTOs.UserDTO;
import com.example.habittracker.inputmodels.CreateCategoryRequest;
import com.example.habittracker.inputmodels.CreateHabitRequest;
import com.example.habittracker.inputmodels.CreateUserRequest;
import com.example.habittracker.services.HabitService;
import com.example.habittracker.services.TaskService;
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

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final HabitService habitService;
    private final UserService userService;
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserRequest request){
        UserDTO dto=userService.createUser(request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @PostMapping("/{id}/habits")
    public ResponseEntity<HabitDTO> createHabitForUser(
            @PathVariable Long id,
           @Valid @RequestBody CreateHabitRequest request
    ){
        HabitDTO dto=habitService.createHabit(id,request.getTitle());
        return ResponseEntity.status((HttpStatus.CREATED)).body(dto);

    }

    @GetMapping("/{id}/habits")
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



    @PostMapping("/{userId}/categories")
    public ResponseEntity<TaskCategoryDTO> createCategory(
            @PathVariable Long userId,
            @Valid @RequestBody CreateCategoryRequest request
    ){
        TaskCategoryDTO dto=taskService.createcategory(userId,request.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{userId}/categories")
    public ResponseEntity<Page<TaskCategoryDTO>> getCategoriesforUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id,asc")String sort
    ){
        String[] sortParam=sort.split(",");
        Sort sorting=sortParam[1].equalsIgnoreCase("desc")
                ?Sort.by(sortParam[0]).descending()
                :Sort.by(sortParam[0]).ascending();
        Pageable pageable=PageRequest.of(page,size,sorting);

        return ResponseEntity.ok(taskService.getCategoryforUser(userId,pageable));
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserwithhabits(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserWithHabits(userId));
    }


}
