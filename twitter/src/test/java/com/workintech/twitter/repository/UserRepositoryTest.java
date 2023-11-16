package com.workintech.twitter.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.workintech.twitter.dao.UserRepository;
import com.workintech.twitter.entity.User;

@SpringBootTest
public class UserRepositoryTest {
    
    private UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void createUsers(){
        User user1 = new User();
        user1.setUsername("kullanici");
        user1.setPassword("123456");
        user1.setEmail("t@t.com");
        user1.setTweets(new ArrayList<>());
        user1.setAuthorities(new ArrayList<>());
        user1.setLikes(new ArrayList<>());
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("user");
        user2.setPassword("123abc");
        user2.setEmail("e@e.com");
        user2.setTweets(new ArrayList<>());
        user2.setAuthorities(new ArrayList<>());
        user2.setLikes(new ArrayList<>());
        userRepository.save(user2);

        User user3 = new User();
        user3.setUsername("user3");
        user3.setPassword("abc456");
        user3.setEmail("test@t.com");
        user3.setTweets(new ArrayList<>());
        user3.setAuthorities(new ArrayList<>());
        user3.setLikes(new ArrayList<>());
        userRepository.save(user3);
    }

    @BeforeEach
    public void setUp(){
        createUsers();
    }


    @AfterEach
    public void tearDown(){
        userRepository.deleteAll();
    }

     @Test
    void canFindByEmail(){
        Optional<User> user = userRepository.findUserByEmail("t@t.com");
        assertNotNull(user.get());
        assertEquals("kullanici", user.get().getUsername());
    }

     @Test
    void isNullWithNonExistingEmail(){
        Optional<User> user = userRepository.findUserByEmail("a@a.com");
        assertEquals(Optional.empty(), user);
    }
}
