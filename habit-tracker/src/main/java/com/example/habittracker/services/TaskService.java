package com.example.habittracker.services;

import com.example.habittracker.DTOs.TaskCategoryDTO;
import com.example.habittracker.DTOs.TaskDTO;
import com.example.habittracker.entities.Task;
import com.example.habittracker.entities.TaskCategory;
import com.example.habittracker.entities.User;
import com.example.habittracker.exception.AccessDeniedException;
import com.example.habittracker.exception.CategoryNotFoundException;
import com.example.habittracker.exception.TaskNotFoundException;
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

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskCategoryRepository taskCategoryRepository;
    private final UserRepository userRepository;

    public TaskDTO createTask(Long TaskCategoryId,Long userId, String title){
        TaskCategory taskCategory=taskCategoryRepository.findById(TaskCategoryId).orElseThrow(()->new CategoryNotFoundException("Task Category don't exist"));

        if (!taskCategory.getUser().getId().equals(userId))throw new AccessDeniedException("access denied");
        Task task=new Task(title,taskCategory);


        taskCategory.getTasks().add(task);
        Task savedTask=taskRepository.save(task);

        TaskDTO taskDTO=new TaskDTO();
        taskDTO.setId(savedTask.getId());
        taskDTO.setTitle(savedTask.getTitle());
        taskDTO.setCompleted(savedTask.isCompleted());
        return taskDTO;




    }

    @Transactional
    public TaskDTO getTaskDone(Long taskid,Long userId){
        Task task=taskRepository.findByIdAndDeletedAtIsNull(taskid).orElseThrow(()->new TaskNotFoundException("task not found"));

        if (!task.getTaskCategory().getUser().getId().equals(userId))throw new AccessDeniedException("access denied");
        task.complete();



        TaskDTO taskDTO=new TaskDTO();
        taskDTO.setCompleted(task.isCompleted());
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        return taskDTO;
    }


    @Transactional
    public void deleteTask(Long taskId,Long userId) {
        Task task=taskRepository.findByIdAndDeletedAtIsNull(taskId).orElseThrow(()->new TaskNotFoundException("the task doesn't exist"));
        if (!task.getTaskCategory().getUser().getId().equals(userId))throw new AccessDeniedException("access denied");
        task.markDeleted();
    }

    public  TaskDTO getTask(Long taskId,Long userId) {
        Task task=taskRepository.findByIdAndDeletedAtIsNull(taskId).orElseThrow(()->new TaskNotFoundException("the task doesn't exist"));
        if (!task.getTaskCategory().getUser().getId().equals(userId))throw new AccessDeniedException("access denied");
        return new TaskDTO(task.getId(),task.getTitle(),task.isCompleted());
    }
}
