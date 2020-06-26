package com.udacity.jwdnd.course1.cloudstorage.controller;
//Made by aditya pathak
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;
import static com.udacity.jwdnd.course1.cloudstorage.controller.BaseController.HOME_REDIRECT;

@Controller
@RequestMapping
public class DefaultController {

    private UserService userService;

    @Autowired
    public DefaultController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute
    @Valid User user) {


        if(user == null){
            return "redirect:/signup";
        }

        if(userService.isUserRegistered(user.getUsername())){

            return "redirect:signup?equalusername";
        }

        try{
            userService.save(user);
        }catch(Exception e){
            return "redirect:signup?error";
        }

        return "redirect:signup?success";
    }

    @GetMapping
    public String root() {
        return HOME_REDIRECT;
    }

    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "/signup";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/result")
    public String result() {
        return "result";
    }
}
