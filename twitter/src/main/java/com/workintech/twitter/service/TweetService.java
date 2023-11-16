package com.workintech.twitter.service;

import java.util.List;

import com.workintech.twitter.dto.TweetResponse;
import com.workintech.twitter.entity.Tweet;


public interface TweetService {
    List<TweetResponse> findAll();
    TweetResponse findById(long id);
    TweetResponse save(Tweet tweet);
    TweetResponse delete(long id);
    TweetResponse saveWithUser(Tweet tweet);
}
