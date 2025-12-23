package com.example.habittracker.controllers;

import com.example.habittracker.DTOs.TaskCategoryDTO;
import com.example.habittracker.DTOs.TaskDTO;
import com.example.habittracker.inputmodels.CreateCategoryRequest;
import com.example.habittracker.inputmodels.RenameCategoryRequest;
import com.example.habittracker.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    //create category for users
    @PostMapping("/users/{userId}/categories")
    public ResponseEntity<TaskCategoryDTO> createCategory(
            @PathVariable Long userId,
            @Valid @RequestBody CreateCategoryRequest request
    ){
        TaskCategoryDTO dto= categoryService.createcategory(userId,request.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }


    //get categories for user as user is in 1:N relationship with Task category
    @GetMapping("/{userId}/categories")
    public ResponseEntity<Page<TaskCategoryDTO>> getCategoriesforUser(
            @PathVariable Long userId,
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ){

        return ResponseEntity.ok(categoryService.getCategoryforUser(userId,pageable));
    }

    @GetMapping("/users/{userId}/category/{categoryId}/tasks")
    public ResponseEntity<Page<TaskDTO>> getTasksByCategory(
            @PathVariable Long categoryId,
            @PathVariable Long userId,
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC
            )
            Pageable pageable
    ) {

        return ResponseEntity.ok(categoryService.getTasksByCategory(categoryId,userId, pageable));
    }
    @GetMapping("/users/{userId}/categories/{categoryId}")
    public ResponseEntity<TaskCategoryDTO> getCategory(@PathVariable Long categoryId,@PathVariable Long userId){
        return ResponseEntity.ok(categoryService.getCategory(categoryId,userId));
    }



    @DeleteMapping("/users/{userId}/category/{categoryId}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long categoryId,
            @PathVariable Long userId
    ){
        categoryService.deleteCategory(categoryId,userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/users/{userId}/category/{categoryId}")
    public ResponseEntity<TaskCategoryDTO> renameCategory(
            @PathVariable Long categoryId,
            @PathVariable Long userId,
            @Valid@RequestBody RenameCategoryRequest request
            ){

        return ResponseEntity.ok(categoryService.renameCategory(categoryId,userId,request));
    }
}
