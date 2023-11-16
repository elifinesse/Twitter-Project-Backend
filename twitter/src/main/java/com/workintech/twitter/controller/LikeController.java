package com.workintech.twitter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workintech.twitter.dto.LikeResponse;
import com.workintech.twitter.entity.Like;
import com.workintech.twitter.service.LikeServiceImpl;

@RestController
@RequestMapping("/like")
public class LikeController {
    
    private LikeServiceImpl likeServiceImpl;

    @Autowired
    public LikeController(LikeServiceImpl likeServiceImpl){
        this.likeServiceImpl = likeServiceImpl;
    }

    @DeleteMapping("/{id}")
    public LikeResponse delete(@PathVariable long id){
        return likeServiceImpl.delete(id);
    }

    @PostMapping("/{tweetId}")
    public LikeResponse saveWithUser(@PathVariable long tweetId, @RequestBody Like like){
        return likeServiceImpl.saveWithUser(tweetId, like);
    }
}
