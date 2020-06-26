package com.udacity.jwdnd.course1.cloudstorage.controller;
//Made by aditya pathak
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.exception.type.MethodNotAllowedException;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController<User> {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        super(userService);
        this.userService = userService;
    }

    @Override
    @ModelAttribute("users")
    public List<User> fetchAll() {
        return super.fetchAll();
    }

    @Override
    public String save(User entity) {
        throw new MethodNotAllowedException("Save method not allowed on users, please use sign-up endpoint");
    }
}
