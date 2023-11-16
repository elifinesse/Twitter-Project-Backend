package com.workintech.twitter.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workintech.twitter.entity.Like;
import com.workintech.twitter.entity.Tweet;
import com.workintech.twitter.entity.User;

public interface LikeRepository extends JpaRepository<Like, Long>{
    boolean existsByUserAndTweet(User user, Tweet tweet);
}
