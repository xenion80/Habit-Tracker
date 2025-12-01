package com.example.habittracker.repositories;

import com.example.habittracker.entities.HabitLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitLogRepository extends JpaRepository<HabitLog, Long> {
}