package com.example.habittracker.repositories;

import com.example.habittracker.entities.Task;
import com.example.habittracker.entities.TaskCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByTaskCategoryId(Long categoryId, Pageable pageable);
    Page<TaskCategory> findByUserId(Long userId, Pageable pageable);
}