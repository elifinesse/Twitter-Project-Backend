package com.workintech.twitter.dto;

import java.util.List;

public record TweetResponse(long id, String username, String content, long likes, List<ReplyResponse> replies) {
    
}
