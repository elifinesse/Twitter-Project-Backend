package com.workintech.twitter.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationUser {

    @Size(min = 3, max = 15, message = "Username cannot be longer than 15 characters or shorter than 3 characters.")
    private String username;

    @Size(min = 6, max = 25, message = "Password cannot be longer than 25 characters or shorter than 6 characters.")
    private String password;

    @Email(message = "Please enter a valid email adress.")
    @NotEmpty
    private String email;
}
