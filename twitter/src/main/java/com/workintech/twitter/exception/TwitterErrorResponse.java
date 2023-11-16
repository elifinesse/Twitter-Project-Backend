package com.workintech.twitter.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TwitterErrorResponse {
    
    private int status;
    private String message;
    private LocalDateTime timestamp;

}
