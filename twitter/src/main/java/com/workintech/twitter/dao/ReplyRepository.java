package com.workintech.twitter.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workintech.twitter.entity.Tweet;

public interface ReplyRepository extends JpaRepository<Tweet, Long>{
    
}
