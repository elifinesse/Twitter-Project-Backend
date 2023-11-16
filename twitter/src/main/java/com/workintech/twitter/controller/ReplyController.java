package com.workintech.twitter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workintech.twitter.dto.ReplyResponse;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.service.ReplyServiceImpl;

@RestController
@RequestMapping("/reply")
public class ReplyController {
    
    private ReplyServiceImpl replyServiceImpl;

    @Autowired
    public ReplyController(ReplyServiceImpl replyServiceImpl){
        this.replyServiceImpl = replyServiceImpl;
    }

    @GetMapping("/{ogTweetId}")
    public List<ReplyResponse> findReplies(@PathVariable long ogTweetId){
        return replyServiceImpl.findReplies(ogTweetId);
    }

    @PostMapping("/{ogTweetId}")
    public ReplyResponse saveReply(@PathVariable long ogTweetId, @RequestBody Tweet tweet){
        return replyServiceImpl.saveReply(ogTweetId, tweet);
    }
}
