package com.example.habittracker.repositories;

import com.example.habittracker.entities.HabitLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface HabitLogRepository extends JpaRepository<HabitLog, Long> {
    Page<HabitLog> findByHabitId(Long habitId, Pageable pageable);
    Page<HabitLog> findByHabitIdAndDateBetween(
            Long habitId,
            LocalDate from,
            LocalDate to,
            Pageable pageable
    );


}