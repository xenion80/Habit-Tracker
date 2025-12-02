package com.example.habittracker.services;

import com.example.habittracker.DTOs.HabitDTO;
import com.example.habittracker.DTOs.HabitLogDTO;
import com.example.habittracker.entities.Habit;
import com.example.habittracker.entities.HabitLog;
import com.example.habittracker.entities.User;
import com.example.habittracker.repositories.HabitLogRepository;
import com.example.habittracker.repositories.HabitRepository;
import com.example.habittracker.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitService {
    private final HabitRepository habitRepository;
    private final HabitLogRepository habitLogRepository;
    private final UserRepository userRepository;

    public HabitDTO createHabit(Long UserId, String title){
        User user=userRepository.findById(UserId).orElseThrow(()->new RuntimeException("User not found"));
        Habit habit=new Habit();
        habit.setTitle(title);
        habit.setStreak(0);
        habit.setUser(user);

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
        Habit habit=habitRepository.findById(HabitId).orElseThrow(()->new RuntimeException("Habit not found"));
        HabitLog log=new HabitLog();
        log.setHabit(habit);
        log.setDate(LocalDate.now());

        habit.getLogs().add(log);
        habit.setStreak(habit.getStreak()+1);

        HabitDTO dto=new HabitDTO();
        dto.setStreak(habit.getStreak());
        dto.setTitle(habit.getTitle());
        dto.setId(habit.getId());
        return dto;

    }
    public List<HabitLogDTO> getLogs(Long HabitId){
        Habit habit=habitRepository.findById(HabitId).orElseThrow(()->new RuntimeException("Habits Not Found"));
        return habit.getLogs().stream().map(log ->{
            HabitLogDTO dto=new HabitLogDTO();
            dto.setId(log.getId());
            dto.setDate(log.getDate());
            return dto;


        }).toList();
    }
    public List<HabitDTO> getAllHabitForUser(Long UserId) {
        User user = userRepository.findById(UserId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getHabits().stream().map(habit -> {
            HabitDTO dto = new HabitDTO();
            dto.setId(habit.getId());
            dto.setTitle(habit.getTitle());
            dto.setStreak(habit.getStreak());
            return dto;
        }).toList();
    }

}
