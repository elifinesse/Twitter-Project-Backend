package com.workintech.twitter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.workintech.twitter.dao.ReplyRepository;
import com.workintech.twitter.dao.TweetRepository;
import com.workintech.twitter.dao.UserRepository;
import com.workintech.twitter.dto.ReplyResponse;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.TwitterException;
import com.workintech.twitter.util.ReplyResponseConverter;

@Service
public class ReplyServiceImpl implements ReplyService{

    private TweetRepository tweetRepository;
    private ReplyRepository replyRepository;
    private UserRepository userRepository;

    @Autowired
    public ReplyServiceImpl(TweetRepository tweetRepository, ReplyRepository replyRepository, UserRepository userRepository){
        this.tweetRepository = tweetRepository;
        this.replyRepository = replyRepository;
        this.userRepository = userRepository;
    }

    private User findAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<User> foundUser = userRepository.findUserByUsername(userDetails.getUsername());
            if(foundUser.isPresent()){
                return foundUser.get();
            }
        }
        throw new TwitterException("Please login.", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ReplyResponse saveReply(long tweetId, Tweet reply) {
        Optional<Tweet> optTweet = tweetRepository.findById(tweetId);
        if(optTweet.isPresent()){
            Tweet found = optTweet.get();
            reply.setReplyTo(found);
        } else{
            throw new TwitterException("Tweet with this id (" + tweetId + ") does not exist.", HttpStatus.NOT_FOUND);
        }

            User foundUser = findAuthenticatedUser();
            reply.setUser(foundUser);
            reply.setReply(true);
            reply.setLikes(new ArrayList<>());
            reply.setReplies(new ArrayList<>());

        replyRepository.save(reply);
        return ReplyResponseConverter.replyConverter(reply);
    }

    @Override
    public List<ReplyResponse> findReplies(long tweetId) {
        Optional<Tweet> optTweet = tweetRepository.findById(tweetId);
        if(optTweet.isPresent()){
            Tweet ogTweet = optTweet.get();
            return ReplyResponseConverter.replyListConverter(ogTweet.getReplies());
        } 
        throw new TwitterException("Tweet with this id (" + tweetId + ") does not exist.", HttpStatus.NOT_FOUND);
    }
  
}
