package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends BaseService<User> {
    User findByUsername(String username) throws UsernameNotFoundException;
    Boolean isUserRegistered(String  username);
}
