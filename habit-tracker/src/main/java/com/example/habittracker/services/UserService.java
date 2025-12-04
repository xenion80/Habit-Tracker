package com.example.habittracker.services;

import com.example.habittracker.DTOs.HabitDTO;
import com.example.habittracker.DTOs.UserDTO;
import com.example.habittracker.entities.User;
import com.example.habittracker.repositories.HabitRepository;
import com.example.habittracker.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final HabitRepository habitRepository;

    public UserDTO createUser(String name){
        User user =new User();
        user.setName(name);

        User savedUser=userRepository.save(user);
        UserDTO userDTO=new UserDTO();
        userDTO.setId(savedUser.getId());
        userDTO.setName(savedUser.getName());

        return userDTO;
    }
    @Transactional
    public UserDTO getUserWithHabits(Long UserId){
        User user=userRepository.findById(UserId).orElseThrow(()->new RuntimeException("User of this id not found"));
        UserDTO userDTO=new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        List<HabitDTO> habitDTOS=user.getHabits().stream().map(habit -> {
            HabitDTO habitDTO=new HabitDTO();
            habitDTO.setId(habit.getId());
            habitDTO.setTitle(habit.getTitle());
            habitDTO.setStreak(habit.getStreak());
            return habitDTO;
        }).toList();
        userDTO.setHabits(habitDTOS);
        return userDTO;
    }
}
