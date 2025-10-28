package com.samuela.grocery.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samuela.grocery.dao.entity.UserInfo;

import java.util.Optional;

@Repository
public interface UserInfoDao extends JpaRepository<UserInfo, Integer> {
	// Use email instead of username because it's a grocery app 
	// I decided we don't need usernames for this website
    Optional<UserInfo> findByEmail(String email); 
}