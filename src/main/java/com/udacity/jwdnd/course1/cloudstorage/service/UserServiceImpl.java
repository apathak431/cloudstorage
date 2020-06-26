package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private UserMapper userMapper;
    private EncryptionService encryptionService;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, EncryptionService encryptionService) {
        super(userMapper);
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        Optional<User> entity = userMapper.findByUsername(username);
        if (username.isEmpty()){
            throw new UsernameNotFoundException("A valid username is required");
        } else if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new UsernameNotFoundException("No valid entry was found for username provided");
        }
    }

    @Override
    public User save(User entity) {
        String encodedKey = encryptionService.generateEncodedKey();
        String encryptedPassword = encryptionService.encryptValue(entity.getPassword(), encodedKey);

        entity.setPassword(encryptedPassword);
        entity.setSalt(encodedKey);

        return super.save(entity);
    }

    public Boolean isUserRegistered(String  username){
        Optional<User> optionalUser = userMapper.findByUsername(username);
        return optionalUser.isPresent();
    }
}
