package com.example.habittracker.services;

import com.example.habittracker.DTOs.TaskCategoryDTO;
import com.example.habittracker.DTOs.TaskDTO;
import com.example.habittracker.entities.Task;
import com.example.habittracker.entities.TaskCategory;
import com.example.habittracker.entities.User;
import com.example.habittracker.exception.CategoryNotFoundException;
import com.example.habittracker.exception.TaskNotFoundException;
import com.example.habittracker.exception.UserNotFoundException;
import com.example.habittracker.repositories.TaskCategoryRepository;
import com.example.habittracker.repositories.TaskRepository;
import com.example.habittracker.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

    public TaskDTO createTask(Long TaskCategoryId, String title){
        TaskCategory taskCategory=taskCategoryRepository.findById(TaskCategoryId).orElseThrow(()->new TaskNotFoundException("Task Category don't exist"));

        Task task=new Task();
        task.setTaskCategory(taskCategory);
        task.setTitle(title);
        task.setCompleted(false);

        taskCategory.getTasks().add(task);
        Task savedTask=taskRepository.save(task);

        TaskDTO taskDTO=new TaskDTO();
        taskDTO.setId(savedTask.getId());
        taskDTO.setTitle(savedTask.getTitle());
        taskDTO.setCompleted(savedTask.isCompleted());
        return taskDTO;




    }

    public TaskDTO getTaskDone(Long taskid){
        Task task=taskRepository.findById(taskid).orElseThrow(()->new TaskNotFoundException("task not found"));

        task.setCompleted(true);
        taskRepository.save(task);


        TaskDTO taskDTO=new TaskDTO();
        taskDTO.setCompleted(task.isCompleted());
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        return taskDTO;
    }


    @Transactional
    public void deleteTask(Long taskId) {
        Task task=taskRepository.findById(taskId).orElseThrow(()->new TaskNotFoundException("the task doesn't exist"));
        taskRepository.delete(task);
    }
}
