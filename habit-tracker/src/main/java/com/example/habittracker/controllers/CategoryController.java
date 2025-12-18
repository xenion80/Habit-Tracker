package com.example.habittracker.controllers;

import com.example.habittracker.DTOs.TaskCategoryDTO;
import com.example.habittracker.DTOs.TaskDTO;
import com.example.habittracker.inputmodels.CreateCategoryRequest;
import com.example.habittracker.inputmodels.CreateTaskRequest;
import com.example.habittracker.services.CategoryService;
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
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id,asc")String sort
    ){
        String[] sortParam=sort.split(",");
        Sort sorting=sortParam[1].equalsIgnoreCase("desc")
                ?Sort.by(sortParam[0]).descending()
                :Sort.by(sortParam[0]).ascending();
        Pageable pageable=PageRequest.of(page,size,sorting);

        return ResponseEntity.ok(categoryService.getCategoryforUser(userId,pageable));
    }

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

        return ResponseEntity.ok(categoryService.getTasksByCategory(categoryId, pageable));
    }



    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
