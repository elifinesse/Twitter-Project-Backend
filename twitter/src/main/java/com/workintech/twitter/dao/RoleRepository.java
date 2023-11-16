package com.workintech.twitter.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.workintech.twitter.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
    
    @Query("SELECT r FROM Role r WHERE r.authority=:authority")
    Optional<Role> findRoleByAuthority(String authority);
}
