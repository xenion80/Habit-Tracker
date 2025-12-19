package com.example.habittracker.entities;

import com.example.habittracker.exception.HabitAlreadyCompletedException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int streak;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private  User user;


    @OneToMany(mappedBy = "habit",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    List<HabitLog> logs=new ArrayList<>();
    private LocalDateTime deletedAt;

    public void markDeleted() {
        if (this.deletedAt == null) {
            this.deletedAt = LocalDateTime.now();
        }
    }
    public boolean isDeleted() {
        return this.deletedAt != null;
    }
    public Habit(String title,User user){
        this.title=title;
        this.user=user;
        this.streak=0;

    }

    public void completeToday() {
        LocalDate today=LocalDate.now();

        boolean alreadyCompleted=logs.stream()
                .anyMatch(
                        habitLog -> habitLog.getDate()
                                .equals(today)
                );
        if(alreadyCompleted)throw new HabitAlreadyCompletedException("Habit already completed today");
        HabitLog log = new HabitLog(this, today);
        this.logs.add(log);
        this.streak++;
    }


}
