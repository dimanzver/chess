package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(HashMap<String,String> data) {
        User user = new User();
        user.setName(data.get("name"));
        user.setEmail(data.get("email"));
        String password = RandomString.make(10);
        user.setPassword((new BCryptPasswordEncoder()).encode(password));
        userRepository.save(user);
        return user;
    }

}
