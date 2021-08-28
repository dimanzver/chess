package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.responses.UserAuthResponse;
import com.example.demo.services.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody String request) {
        HashMap<String, String> requestData = new HashMap<>();
        requestData = (HashMap<String, String>) (new Gson()).fromJson(request, requestData.getClass());
        return userService.register(requestData);
    }

    @PostMapping("/login")
    public UserAuthResponse login(@RequestBody String request, HttpServletResponse response) {
        HashMap<String, String> requestData = new HashMap<>();
        requestData = (HashMap<String, String>) (new Gson()).fromJson(request, requestData.getClass());
        UserAuthResponse userResponse = userService.login(requestData);
        if(userResponse.getUser() == null) {
            response.setStatus(401);
        }
        return userResponse;
    }

}
