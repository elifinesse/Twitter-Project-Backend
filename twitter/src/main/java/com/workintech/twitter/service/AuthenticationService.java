package com.workintech.twitter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.workintech.twitter.dao.RoleRepository;
import com.workintech.twitter.dao.UserRepository;
import com.workintech.twitter.dto.UserResponse;
import com.workintech.twitter.entity.Role;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.TwitterException;

@Service
public class AuthenticationService {
    
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse register(String username, String password, String email){
        Optional<User> foundByEmail = userRepository.findUserByEmail(email);
        if(foundByEmail.isPresent()){
            throw new TwitterException("There is already an account associated with this email. Please login.", HttpStatus.BAD_REQUEST);
        }

        Optional<User> foundByUsername = userRepository.findUserByUsername(username);
        if(foundByUsername.isPresent()){
            throw new TwitterException("This username is in use. Please choose another one.", HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findRoleByAuthority("USER").get();

        List<Role> roleList = new ArrayList<>();
        roleList.add(userRole);

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(encodedPassword);
        User registered = userRepository.save(newUser);

        return new UserResponse(registered.getId(), registered.getUsername(), registered.getPassword(), registered.getEmail());
    }

    public UserResponse login(String email, String password){
        Optional<User> optUser = userRepository.findUserByEmail(email);
        if(optUser.isPresent()){
            User foundUser = optUser.get();
            if(passwordEncoder.matches(password, foundUser.getPassword())){
                return new UserResponse(foundUser.getId(), foundUser.getUsername(), foundUser.getPassword(), foundUser.getEmail());
            }
            throw new TwitterException("Invalid credentials.", HttpStatus.BAD_REQUEST);
        }
        throw new TwitterException("Invalid credentials", HttpStatus.BAD_REQUEST);
    }

}
