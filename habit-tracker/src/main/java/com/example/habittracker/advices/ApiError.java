package com.example.habittracker.advices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ApiError {
    private int status;
    private List<String> message;
    private LocalDateTime timestamp;
}
