package com.workintech.twitter.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.workintech.twitter.dao.LikeRepository;
import com.workintech.twitter.dao.TweetRepository;
import com.workintech.twitter.dao.UserRepository;
import com.workintech.twitter.dto.LikeResponse;
import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;
import com.workintech.twitter.exception.TwitterException;

@Service
public class LikeServiceImpl implements LikeService{
    
    private LikeRepository likeRepository;
    private TweetRepository tweetRepository;
    private UserRepository userRepository;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, TweetRepository tweetRepository, UserRepository userRepository){
        this.likeRepository = likeRepository;
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @Override
    public LikeResponse save(long tweetId, Like like) {
        Optional<Tweet> optTweet = tweetRepository.findById(tweetId);
        if(optTweet.isPresent()){
            Tweet found = optTweet.get();
            found.getLikes().add(like);
            like.setTweet(found);
            likeRepository.save(like);
            return new LikeResponse(like.getId(), tweetId);
        }
        throw new TwitterException("Tweet with this id (" + tweetId + ") does not exist.", HttpStatus.NOT_FOUND);
    }

    @Override
    public LikeResponse delete(long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User foundUser = userRepository.findByUsername(userDetails.getUsername());


        Optional<Like> optLike = likeRepository.findById(id);
        if(optLike.isPresent()){
            Like found = optLike.get();
            if(foundUser.equals(found.getUser())){
                likeRepository.deleteById(id);
                return new LikeResponse(found.getId(), found.getTweet().getId());
            }
            throw new TwitterException("Not authorized to delete this like.", HttpStatus.BAD_REQUEST);  
        }
        throw new TwitterException("Like with this id (" + id + ") does not exist.", HttpStatus.NOT_FOUND);
    }

    @Override
    public LikeResponse saveWithUser(long tweetId, Like like) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User foundUser = userRepository.findByUsername(userDetails.getUsername());

        Optional<Tweet> optTweet = tweetRepository.findById(tweetId);
        if(optTweet.isPresent()){
            Tweet foundTweet = optTweet.get();
            if (hasUserLikedTweet(foundUser, foundTweet)) {
                throw new TwitterException("You already liked this tweet.", HttpStatus.BAD_REQUEST);
            }
            like.setUser(foundUser);
            like.setTweet(foundTweet);

            likeRepository.save(like);
            return new LikeResponse(like.getId(), tweetId);
        }
        throw new TwitterException("Tweet with this id (" + tweetId + ") does not exist.", HttpStatus.NOT_FOUND);
    }

    private boolean hasUserLikedTweet(User user, Tweet tweet) {
        return likeRepository.existsByUserAndTweet(user, tweet);
    }

}
