package com.example.habittracker.repositories;

import com.example.habittracker.DTOs.HabitDTO;
import com.example.habittracker.entities.Habit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    Page<Habit> findByUserId(Long UserId, Pageable pageable);
}