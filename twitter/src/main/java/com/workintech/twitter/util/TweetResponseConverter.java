package com.workintech.twitter.util;

import java.util.ArrayList;
import java.util.List;

import com.workintech.twitter.dto.ReplyResponse;
import com.workintech.twitter.dto.TweetResponse;
import com.workintech.twitter.entity.Tweet;


public class TweetResponseConverter {
    
    public static TweetResponse tweetConverter(Tweet tweet){
        List<ReplyResponse> replyResponses = new ArrayList<>();

        for(Tweet reply: tweet.getReplies()){
            ReplyResponse response = ReplyResponseConverter.replyConverter(reply);
            replyResponses.add(response);
        }

        TweetResponse response = new TweetResponse(tweet.getId(), tweet.getUser().getUsername(), tweet.getContent(), tweet.getLikes().size(), replyResponses);

        return response;
    }

    public static List<TweetResponse> tweetResponseListConverter(List<Tweet> tweetList){
        List<TweetResponse> responseList = new ArrayList<>();
        for(Tweet tweet: tweetList){
            TweetResponse response = tweetConverter(tweet);
            responseList.add(response);
        }

        return responseList;
    }
}
