package com.example.habittracker.advices;

import com.example.habittracker.exception.CategoryNotFoundException;
import com.example.habittracker.exception.HabitNotFoundException;
import com.example.habittracker.exception.TaskNotFoundException;
import com.example.habittracker.exception.UserNotFoundException;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> userNotFound(UserNotFoundException exception){
        ApiError apiError=ApiError.builder()
                .status(404)
                .message(Collections.singletonList(exception.getMessage()))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);

    }
    @ExceptionHandler(HabitNotFoundException.class)
    public ResponseEntity<ApiError> habitNotFound(HabitNotFoundException exception){
        ApiError apiError=ApiError.builder()
                .status(404)
                .message(Collections.singletonList(exception.getMessage()))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);

    }
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiError> TaskNotFound(TaskNotFoundException exception){
        ApiError apiError=ApiError.builder()
                .status(404)
                .message(Collections.singletonList(exception.getMessage()))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);

    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiError> CategoryNotFound(CategoryNotFoundException exception){
        ApiError apiError=ApiError.builder()
                .status(404)
                .message(Collections.singletonList(exception.getMessage()))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException exception){
        List<String> message=exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error->error.getDefaultMessage())
                .toList();

        ApiError apiError=ApiError.builder()
                .status(400)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccess(AccessDeniedException exception){
        ApiError apiError=ApiError.builder()
                .status(403)
                .message(Collections.singletonList(exception.getMessage()))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiError> otherException(IllegalStateException exception){
        ApiError apiError=ApiError.builder()
                .status(409)
                .message(Collections.singletonList(exception.getMessage()))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

}
