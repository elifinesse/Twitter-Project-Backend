package com.workintech.twitter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.workintech.twitter.dao.TweetRepository;
import com.workintech.twitter.dao.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.workintech.twitter.dto.TweetResponse;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.TwitterException;
import com.workintech.twitter.util.TweetResponseConverter;

@Service
public class TweetServiceImpl implements TweetService{

    private TweetRepository tweetRepository;
    private UserRepository userRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository, UserRepository userRepository){
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TweetResponse> findAll() {
        List<Tweet> allTweets = tweetRepository.findAll();
        return TweetResponseConverter.tweetResponseListConverter(allTweets);
    }


    @Override
    public TweetResponse findById(long id) {
        Optional<Tweet> optTweet = tweetRepository.findById(id);
        if(optTweet.isPresent()){
            return TweetResponseConverter.tweetConverter(optTweet.get());
        }
        throw new TwitterException("Tweet with this id (" + id + ") does not exist.", HttpStatus.NOT_FOUND);
    }

    @Override
    public TweetResponse save(Tweet tweet) {
        Tweet savedTweet = tweetRepository.save(tweet);
        return TweetResponseConverter.tweetConverter(savedTweet);
    }

    @Override
    public TweetResponse delete(long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Tweet> optTweet = tweetRepository.findById(id);
        if(authentication != null && authentication.isAuthenticated()){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User foundUser = userRepository.findByUsername(userDetails.getUsername());
            if(optTweet.isPresent() && optTweet.get().getUser().getId() == foundUser.getId()){
                tweetRepository.deleteById(id);
                return TweetResponseConverter.tweetConverter(optTweet.get());
            }
        }

        throw new TwitterException("Tweet with this id (" + id + ") does not exist. / You are not authorized to delete it.", HttpStatus.NOT_FOUND);
    }

    @Override
    public TweetResponse saveWithUser(Tweet tweet) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<User> foundUser = userRepository.findUserByUsername(userDetails.getUsername());
            if(foundUser.isPresent()){
                User loggedInUser = foundUser.get();
                tweet.setUser(loggedInUser);
                tweet.setReplies(new ArrayList<>());
                tweet.setLikes(new ArrayList<>());
                return save(tweet);
            }
            throw new TwitterException("User not found.", HttpStatus.NOT_FOUND);
        } else {
            throw new TwitterException("User not authenticated", HttpStatus.BAD_REQUEST);
        }
    }
    
}
