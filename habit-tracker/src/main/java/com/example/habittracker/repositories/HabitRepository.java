package com.example.habittracker.repositories;

import com.example.habittracker.entities.Habit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    Page<Habit> findByUserIdAndDeletedAtIsNull(Long UserId, Pageable pageable);
    Optional<Habit> findByIdAndDeletedAtIsNull(Long id);

}