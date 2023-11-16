package com.workintech.twitter.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwitterException extends RuntimeException{
    
    private HttpStatus status;

    public TwitterException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }
}
