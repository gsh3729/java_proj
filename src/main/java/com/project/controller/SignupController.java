package com.project.controller;

import com.project.model.User;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SignupController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public String signup(@ModelAttribute User user, Model model) {
        User user1 = userService.createUser(user);
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String showRegistrationForm(Model model) {
//        model.addAttribute("user", new User());
        return "signup";
    }

}

