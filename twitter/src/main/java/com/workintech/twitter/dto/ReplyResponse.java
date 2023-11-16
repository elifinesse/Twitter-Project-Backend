package com.workintech.twitter.dto;

public record ReplyResponse(long id, String username, String content, long ogTweetId, long likes) {
    
}
