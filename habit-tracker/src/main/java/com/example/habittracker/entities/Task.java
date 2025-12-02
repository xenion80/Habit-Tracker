package com.example.habittracker.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

     private boolean completed;

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "task_category_id")
     private TaskCategory taskCategory;


}
