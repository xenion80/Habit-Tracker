package com.example.habittracker.services;

import com.example.habittracker.DTOs.HabitDTO;
import com.example.habittracker.DTOs.HabitLogDTO;
import com.example.habittracker.entities.Habit;
import com.example.habittracker.entities.HabitLog;
import com.example.habittracker.entities.User;
import com.example.habittracker.exception.HabitNotFoundException;
import com.example.habittracker.exception.UserNotFoundException;
import com.example.habittracker.repositories.HabitLogRepository;
import com.example.habittracker.repositories.HabitRepository;
import com.example.habittracker.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class HabitService {
    private final HabitRepository habitRepository;
    private final HabitLogRepository habitLogRepository;
    private final UserRepository userRepository;

    public HabitDTO createHabit(Long UserId, String title){
        User user=userRepository.findById(UserId).orElseThrow(()->new UserNotFoundException("User not found"));
        Habit habit=new Habit(title,user);

        user.getHabits().add(habit);

        Habit savedHabit=habitRepository.save(habit);

        HabitDTO dto=new HabitDTO();
        dto.setId(habit.getId());
        dto.setTitle(habit.getTitle());
        dto.setStreak(habit.getStreak());
        return dto;

    }
    @Transactional
    public HabitDTO completeHabit(Long HabitId){
        Habit habit=habitRepository.findByIdAndDeletedAtIsNull(HabitId).orElseThrow(()->new HabitNotFoundException("Habit not found"));
        habit.completeToday();

        HabitDTO dto=new HabitDTO();
        dto.setStreak(habit.getStreak());
        dto.setTitle(habit.getTitle());
        dto.setId(habit.getId());
        return dto;

    }
    public Page<HabitLogDTO> getLogs(Long habitId, LocalDate from, LocalDate to, Pageable pageable) {

        Page<HabitLog> pageResult;

        if (from != null && to != null) {
            pageResult = habitLogRepository.findByHabitIdAndDateBetween(habitId, from, to, pageable);
        } else {
            pageResult = habitLogRepository.findByHabitId(habitId, pageable);
        }

        return pageResult.map(log -> new HabitLogDTO(
                log.getId(),
                log.getDate()
        ));
    }

    @Transactional
    public Page<HabitDTO> getAllHabitForUser(Long UserId, Pageable pageable) {
       Page<Habit> habits=habitRepository.findByUserIdAndDeletedAtIsNull(UserId,pageable);
        return habits.map(habit -> new HabitDTO(
                habit.getId(),
                habit.getTitle(),
                habit.getStreak()
        ));
    }

    @Transactional
    public void deleteHabit(Long habitId) {
        Habit habit=habitRepository.findByIdAndDeletedAtIsNull(habitId).orElseThrow(()->new HabitNotFoundException("Habit is not found"));
        habit.markDeleted();
    }
}
