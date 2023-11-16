package com.workintech.twitter.service;

import java.util.List;

import com.workintech.twitter.dto.ReplyResponse;
import com.workintech.twitter.entity.Tweet;

public interface ReplyService {
    ReplyResponse saveReply(long tweetId, Tweet reply);
    List<ReplyResponse> findReplies(long tweetId);
    
}
