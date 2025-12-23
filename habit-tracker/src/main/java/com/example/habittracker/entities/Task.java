package com.example.habittracker.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_category_id", nullable = false)
    private TaskCategory taskCategory;

    private LocalDateTime deletedAt;

    // ---- Domain actions ----

    public void complete() {
        this.completed = true;
    }

    public void markDeleted() {
        if (this.deletedAt == null) {
            this.deletedAt = LocalDateTime.now();
        }
    }

    // Optional but useful
    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    public Task(String title, TaskCategory taskCategory) {
        this.title = title;
        this.taskCategory = taskCategory;
        this.completed = false;
        this.deletedAt = null;
    }

    public void rename(String newTitle) {
        if (this.deletedAt!=null)throw new IllegalStateException("cannot rename a deleted task task");
        if (newTitle == null || newTitle.isBlank()) {
            throw new IllegalArgumentException("Habit title cannot be blank");
        }
        this.title=newTitle;
    }
}
