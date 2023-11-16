package com.workintech.twitter.service;

import com.workintech.twitter.dto.LikeResponse;
import com.workintech.twitter.entity.Like;

public interface LikeService {

    LikeResponse save(long tweetId, Like like);
    LikeResponse saveWithUser(long tweetId, Like like);
    LikeResponse delete(long id);
}
