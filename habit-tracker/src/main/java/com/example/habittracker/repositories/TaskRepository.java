package com.example.habittracker.repositories;

import com.example.habittracker.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByTaskCategoryIdAndDeletedAtIsNull(Long categoryId, Pageable pageable);

    Optional<Task> findByIdAndDeletedAtIsNull(Long id);
    Page<Task> findByTaskCategoryIdAndDeletedAtIsNullAndTaskCategoryDeletedAtIsNull(Long categoryId, Pageable pageable);


}