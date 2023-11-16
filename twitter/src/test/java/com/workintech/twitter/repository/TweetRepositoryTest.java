package com.workintech.twitter.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.workintech.twitter.dao.TweetRepository;

@SpringBootTest
public class TweetRepositoryTest {
    
    private TweetRepository tweetRepository;

    @Autowired
    public TweetRepositoryTest(TweetRepository tweetRepository){
        this.tweetRepository = tweetRepository;
    }


}
