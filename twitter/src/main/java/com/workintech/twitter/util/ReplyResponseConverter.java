package com.workintech.twitter.util;

import java.util.ArrayList;
import java.util.List;

import com.workintech.twitter.dto.ReplyResponse;
import com.workintech.twitter.entity.Tweet;

public class ReplyResponseConverter {
    

    public static ReplyResponse replyConverter(Tweet reply){
        ReplyResponse replyResponse = new ReplyResponse(reply.getId(), reply.getUser().getUsername(), reply.getContent(), reply.getReplyTo().getId(), reply.getLikes().size());
        
        return replyResponse;
    }

    public static List<ReplyResponse> replyListConverter(List<Tweet> replies){
        List<ReplyResponse> replyResponseList = new ArrayList<>();
        for(Tweet reply: replies){
            ReplyResponse replyResponse = replyConverter(reply);
            replyResponseList.add(replyResponse);
        }
        
        return replyResponseList;
    }
}
