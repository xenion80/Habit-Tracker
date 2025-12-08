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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public TaskCategoryDTO createcategory(Long userId,String name){
        User user=userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
        TaskCategory taskCategory=new TaskCategory();
        taskCategory.setName(name);
        taskCategory.setUser(user);
        TaskCategory saved=taskCategoryRepository.save(taskCategory);

        TaskCategoryDTO taskCategoryDTO=new TaskCategoryDTO();
        taskCategoryDTO.setId(saved.getId());
        taskCategoryDTO.setTitle(saved.getName());
        return taskCategoryDTO;

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

    public Page<TaskDTO> getTasksByCategory(Long categoryId, Pageable pageable) {

        Page<Task> pageResult = taskRepository.findByTaskCategoryId(categoryId, pageable);

        return pageResult.map(task -> new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.isCompleted()
        ));
    }


    public List<TaskCategoryDTO> getCategoryforUser(Long UserId){
        User user=userRepository.findById(UserId).orElseThrow(()->new UserNotFoundException("User not found"));
        return user.getCategories().stream().map(taskCategory -> new TaskCategoryDTO(taskCategory.getId(),taskCategory.getName())).toList();
    }
}
