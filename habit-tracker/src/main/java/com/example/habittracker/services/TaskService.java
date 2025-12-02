package com.example.habittracker.services;

import com.example.habittracker.DTOs.TaskCategoryDTO;
import com.example.habittracker.DTOs.TaskDTO;
import com.example.habittracker.entities.Task;
import com.example.habittracker.entities.TaskCategory;
import com.example.habittracker.repositories.TaskCategoryRepository;
import com.example.habittracker.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskCategoryRepository taskCategoryRepository;

    public TaskDTO createTask(Long TaskCategoryId, String title){
        TaskCategory taskCategory=taskCategoryRepository.findById(TaskCategoryId).orElseThrow(()->new RuntimeException("Task Category don't exist"));

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
    public TaskCategoryDTO createcategory(String name){
        TaskCategory taskCategory=new TaskCategory();
        taskCategory.setName(name);
        TaskCategory saved=taskCategoryRepository.save(taskCategory);

        TaskCategoryDTO taskCategoryDTO=new TaskCategoryDTO();
        taskCategoryDTO.setId(saved.getId());
        taskCategoryDTO.setName(saved.getName());
        return taskCategoryDTO;

    }
    public TaskDTO getTaskDone(Long TaskId){
        Task task=taskRepository.findById(TaskId).orElseThrow(()->new RuntimeException("task not found"));

        task.setCompleted(true);


        TaskDTO taskDTO=new TaskDTO();
        taskDTO.setCompleted(task.isCompleted());
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        return taskDTO;
    }

    public List<TaskDTO> getTaskByCategory(Long TaskCategoryId){
        TaskCategory taskCategory =taskCategoryRepository.findById(TaskCategoryId).orElseThrow(()->new RuntimeException("The category not found"));
        return taskCategory.getTasks().stream().map(task -> {
            TaskDTO taskDTO=new TaskDTO();
            taskDTO.setTitle(task.getTitle());
            taskDTO.setCompleted(task.isCompleted());
            taskDTO.setId(task.getId());
            return taskDTO;
        }).toList();
    }
}
