package com.example.habittracker.repositories;

import com.example.habittracker.entities.TaskCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskCategoryRepository extends JpaRepository<TaskCategory, Long> {

    Page<TaskCategory> findByUserIdAndDeletedAtIsNull(Long userId, Pageable pageable);
    Optional<TaskCategory> findByIdAndDeletedAtIsNull(Long id);


}