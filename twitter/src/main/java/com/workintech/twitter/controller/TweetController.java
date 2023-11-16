package com.workintech.twitter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workintech.twitter.dto.TweetResponse;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.TwitterException;
import com.workintech.twitter.service.TweetServiceImpl;
import com.workintech.twitter.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/tweet")
public class TweetController {
    
    private TweetServiceImpl tweetServiceImpl;
    private UserService userService;

    @Autowired
    public TweetController(TweetServiceImpl tweetServiceImpl, UserService userService){
        this.tweetServiceImpl = tweetServiceImpl;
        this.userService = userService;
    }

    @GetMapping("/")
    public List<TweetResponse> findAll(){
        return tweetServiceImpl.findAll();
    }

    @GetMapping("/{id}")
    public TweetResponse findById(@PathVariable long id){
        return tweetServiceImpl.findById(id);
    }

    @PostMapping("/")
    public TweetResponse saveWithUser(@RequestBody Tweet tweet){
        return tweetServiceImpl.saveWithUser(tweet);
    }

    @PostMapping("/saveByUser/{id}")
    public TweetResponse saveByUserId(@PathVariable long id, @RequestBody Tweet tweet){
        User found = userService.findById(id);
        tweet.setUser(found);
        return tweetServiceImpl.save(tweet);
    }

    @DeleteMapping("/{id}")
    public TweetResponse delete(@PathVariable long id){
        return tweetServiceImpl.delete(id);
    }
}

