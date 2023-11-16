package com.workintech.twitter.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler
    public ResponseEntity<TwitterErrorResponse> handleException(TwitterException exception){
        TwitterErrorResponse errorResponse = new TwitterErrorResponse(exception.getStatus().value(), exception.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, exception.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException exception) {
        List<Map<String, String>> errors = exception
            .getBindingResult().getFieldErrors()
            .stream()
            .map(err -> {
                Map<String, String> errMap = new HashMap<>();
                errMap.put(err.getField(), err.getDefaultMessage());
                return errMap;
            })
            .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler
    public ResponseEntity<TwitterErrorResponse> handleException(Exception exception){
        TwitterErrorResponse errorResponse = new TwitterErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
