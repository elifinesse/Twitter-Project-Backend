package com.workintech.twitter.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.workintech.twitter.dao.UserRepository;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.TwitterException;

@Service
public class UserService implements UserDetailsService{

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User credentials are not valid."));
    }
    
    public User findById(long id){
        Optional<User> optUser = userRepository.findById(id);
        if(optUser.isPresent()){
            return optUser.get();    
        }
        throw new TwitterException("User with this id (" + id + ") does not exist.", HttpStatus.NOT_FOUND);
    }

    public User findByUsername(String username){
        Optional<User> optUser = userRepository.findUserByUsername(username);
        if(optUser.isPresent()){
            return optUser.get();
        }
        throw new TwitterException("User with this username (" + username + ") does not exist.", HttpStatus.NOT_FOUND);
    }
}
