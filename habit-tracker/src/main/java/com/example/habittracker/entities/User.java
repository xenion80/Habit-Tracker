package com.example.habittracker.entities;

import com.example.habittracker.DTOs.HabitDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "user",
    orphanRemoval = true,
    fetch = FetchType.LAZY,
    cascade = CascadeType.ALL)
    private List<Habit> habits=new ArrayList<>();
}
