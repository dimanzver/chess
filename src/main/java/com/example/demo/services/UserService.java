package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.modules.common.requests.LoginRequestData;
import com.example.demo.modules.common.requests.RegisterRequestData;
import com.example.demo.repositories.UserRepository;
import com.example.demo.modules.common.responses.UserAuthResponse;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    public User register(RegisterRequestData data) {
        User user = new User();
        user.setName(data.getName());
        user.setEmail(data.getEmail());
        String password = RandomString.make(10);
        System.out.println(password);
        user.setPassword((new BCryptPasswordEncoder()).encode(password));
        userRepository.save(user);
        return user;
    }

    public UserAuthResponse login(LoginRequestData data) {
        User user = userRepository.findByEmail(data.getEmail());
        String password = data.getPassword();
        UserAuthResponse response = new UserAuthResponse();
        if(user != null && (new BCryptPasswordEncoder()).matches(password != null ? password : "", user.getPassword())) {
            response.setUser(user);
            response.setToken(jwtService.generateUserToken(user.getId()));
        }
        return response;
    }
}
