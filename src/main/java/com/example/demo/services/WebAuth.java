package com.example.demo.services;

import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class WebAuth {
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    public WebAuth(UserService userService) {
        this.userService = userService;
    }

    public User authenticate() {
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return userService.authenticate(authHeader);
    }
}