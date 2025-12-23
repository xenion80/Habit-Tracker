package com.example.habittracker.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @OneToMany(
            mappedBy = "taskCategory",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Task> tasks=new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime deletedAt;

    public void markedDeleted(){
        if(this.deletedAt==null){
            this.deletedAt=LocalDateTime.now();
        }
    }
    public boolean isDeleted(){
        return this.deletedAt!=null;
    }

    public TaskCategory(String name,User user){
        this.name=name;
        this.user=user;
    }

    public void rename(String newTitle){
        if(this.deletedAt!=null)throw new IllegalStateException("cannot rename a deleted task category");
        if (newTitle == null || newTitle.isBlank()) {
            throw new IllegalArgumentException("Habit title cannot be blank");
        }
        this.name=newTitle;
    }

}
