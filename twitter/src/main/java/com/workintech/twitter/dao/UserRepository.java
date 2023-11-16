package com.workintech.twitter.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.workintech.twitter.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
    @Query("SELECT u FROM User u WHERE u.email=:email")
    Optional<User> findUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.username=:username")
    Optional<User> findUserByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username=:username")
    User findByUsername(String username);
}
