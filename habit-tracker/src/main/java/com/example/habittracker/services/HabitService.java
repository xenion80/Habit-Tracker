package com.example.habittracker.services;

import com.example.habittracker.DTOs.HabitDTO;
import com.example.habittracker.DTOs.HabitLogDTO;
import com.example.habittracker.entities.Habit;
import com.example.habittracker.entities.HabitLog;
import com.example.habittracker.entities.User;
import com.example.habittracker.exception.AccessDeniedException;
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
    public HabitDTO completeHabit(Long habitId, Long userId) {
        Habit habit=habitRepository.findByIdAndDeletedAtIsNull(habitId).orElseThrow(()->new HabitNotFoundException("Habit not found"));
        if (!habit.getUser().getId().equals(userId))throw new AccessDeniedException("you don't own this habit");
        habit.completeToday();

        HabitDTO dto=new HabitDTO();
        dto.setStreak(habit.getStreak());
        dto.setTitle(habit.getTitle());
        dto.setId(habit.getId());
        return dto;

    }
    public Page<HabitLogDTO> getLogs(Long habitId,Long userId, LocalDate from, LocalDate to, Pageable pageable) {

        Habit habit=habitRepository.findByIdAndDeletedAtIsNull(userId).orElseThrow(()->new HabitNotFoundException("habit doesn't exist"));
        if (habit.getUser().getId().equals(userId))throw new AccessDeniedException("you don't have to the logs");

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
    public HabitDTO getHabitById(Long habitId, Long userId){
        Habit habit=habitRepository.findByIdAndDeletedAtIsNull(habitId).orElseThrow(()->new HabitNotFoundException("Habit doesn't exist"));
        if (!habit.getUser().getId().equals(userId))throw new AccessDeniedException("you dont have access to this habit");
        return new HabitDTO(habit.getId(),habit.getTitle(),habit.getStreak());

    }

    @Transactional
    public void deleteHabit(Long habitId,Long userId) {
        Habit habit=habitRepository.findByIdAndDeletedAtIsNull(habitId).orElseThrow(()->new HabitNotFoundException("Habit is not found"));
        if(!habit.getUser().getId().equals(userId))throw new AccessDeniedException("You don't have the access to delete the the habit");
        habit.markDeleted();
    }
}
