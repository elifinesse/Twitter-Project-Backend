package com.workintech.twitter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workintech.twitter.dto.LoginUser;
import com.workintech.twitter.dto.RegistrationUser;
import com.workintech.twitter.dto.UserResponse;
import com.workintech.twitter.service.AuthenticationService;

@Validated
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public UserResponse register(@Validated @RequestBody RegistrationUser user){
       return authenticationService.register(user.getUsername(), user.getPassword(), user.getEmail());
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody LoginUser user){
        return authenticationService.login(user.email(), user.password());
    }
}
