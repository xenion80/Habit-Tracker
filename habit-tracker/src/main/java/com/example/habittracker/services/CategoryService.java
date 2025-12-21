package com.example.habittracker.services;

import com.example.habittracker.DTOs.TaskCategoryDTO;
import com.example.habittracker.DTOs.TaskDTO;
import com.example.habittracker.entities.Task;
import com.example.habittracker.entities.TaskCategory;
import com.example.habittracker.entities.User;
import com.example.habittracker.exception.AccessDeniedException;
import com.example.habittracker.exception.CategoryNotFoundException;
import com.example.habittracker.exception.UserNotFoundException;
import com.example.habittracker.repositories.TaskCategoryRepository;
import com.example.habittracker.repositories.TaskRepository;
import com.example.habittracker.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final UserRepository userRepository;
    private final TaskCategoryRepository taskCategoryRepository;
    private final TaskRepository taskRepository;

    public TaskCategoryDTO createcategory(Long userId, String name){
        User user=userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
        TaskCategory taskCategory=new TaskCategory(name,user);

        TaskCategory saved=taskCategoryRepository.save(taskCategory);

        TaskCategoryDTO taskCategoryDTO=new TaskCategoryDTO();
        taskCategoryDTO.setId(saved.getId());
        taskCategoryDTO.setTitle(saved.getName());
        return taskCategoryDTO;

    }

    public Page<TaskDTO> getTasksByCategory(Long categoryId,Long userId, Pageable pageable) {

        TaskCategory taskCategory=taskCategoryRepository.findByIdAndDeletedAtIsNull(categoryId).orElseThrow(()->new CategoryNotFoundException("the task category doesn't exist"));
        if(!taskCategory.getUser().getId().equals(userId))throw new AccessDeniedException("you dont have access to the category");
        Page<Task> pageResult = taskRepository.findByTaskCategoryIdAndDeletedAtIsNullAndTaskCategoryDeletedAtIsNull(categoryId, pageable);

        return pageResult.map(task -> new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.isCompleted()
        ));
    }
    public Page<TaskCategoryDTO> getCategoryforUser(Long userId,Pageable pageable){
        Page<TaskCategory> result=taskCategoryRepository.findByUserIdAndDeletedAtIsNull(userId,pageable);
        return result.map(taskCategory -> new TaskCategoryDTO(
                taskCategory.getId(),
                taskCategory.getName()


        ));
    }
    @Transactional
    public void deleteCategory(Long categoryId,Long userId) {
        TaskCategory category=taskCategoryRepository.findByIdAndDeletedAtIsNull(categoryId).orElseThrow(()->new CategoryNotFoundException("Category not found"));
        if(category.getUser().getId().equals(userId))throw new AccessDeniedException("you don't have aceess to delete the category");
        category.markedDeleted();
    }

    public  TaskCategoryDTO getCategory(Long categoryId,Long userId) {
        TaskCategory taskCategory=taskCategoryRepository.findByIdAndDeletedAtIsNull(categoryId).orElseThrow(()->new CategoryNotFoundException("Category not found"));
        if (!taskCategory.getUser().getId().equals(userId))throw new AccessDeniedException("you dont have access");
        return new TaskCategoryDTO(taskCategory.getId(),taskCategory.getName());
    }
}
