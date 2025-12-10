package com.example.habittracker.repositories;

import com.example.habittracker.entities.TaskCategory;
import com.example.habittracker.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}