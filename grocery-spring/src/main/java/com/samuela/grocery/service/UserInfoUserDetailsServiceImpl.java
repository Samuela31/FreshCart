package com.samuela.grocery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.samuela.grocery.dao.UserInfoDao;
import com.samuela.grocery.dao.entity.UserInfo;

import java.util.Optional;

@Service
public class UserInfoUserDetailsServiceImpl implements UserDetailsService {
//UserDetailsService is from spring security not mine
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoDao.findByEmail(email);
        // Return-type UserDetails is in-built in spring security core (see imports on top)
        
        return userInfo
            .map(user-> new UserInfoUserDetails(
                user.getEmail(),
                user.getPassword(),
                user.getAllRoles()
            ))
            .orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
    }

}
