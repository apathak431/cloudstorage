package com.udacity.jwdnd.course1.cloudstorage.security.service;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.security.entity.PrincipleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrincipleUserServiceImpl implements UserDetailsService {

    private UserMapper userMapper;

    @Autowired
    public PrincipleUserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> entity = userMapper.findByUsername(s);
        if (s.isEmpty()){
            throw new UsernameNotFoundException("A valid username is required");
        } else if (entity.isPresent()) {
            return new PrincipleUser(entity.get());
        } else {
            throw new UsernameNotFoundException("No valid entry was found for username provided");
        }
    }
}
