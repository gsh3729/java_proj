package com.project.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.project.model.User;
import com.project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session) {
        // Authenticate user
        User user = userService.authenticate(username, password);
        if (user == null) {
            return "loginfail";
        }

        // Save user id in session
        session.setAttribute("userId", user.getUserId());

        // Redirect to main page
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidate session
        session.invalidate();

        // Redirect to login page
        return "redirect:/login";
    }
}

