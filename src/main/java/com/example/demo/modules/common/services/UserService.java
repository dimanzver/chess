package com.example.demo.modules.common.services;

import com.example.demo.models.User;
import com.example.demo.modules.common.requests.LoginRequestData;
import com.example.demo.modules.common.requests.RegisterRequestData;
import com.example.demo.repositories.UserRepository;
import com.example.demo.modules.common.responses.UserAuthResponse;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    public User register(RegisterRequestData data) {
        return register(data.getName(), data.getEmail());
    }

    public User register(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
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

    public User authenticate(String token) {
        Integer userId = this.jwtService.getUserId(token);
        if(userId == null) return null;
        Optional<User> user = this.userRepository.findById(userId);
        return user.orElse(null);
    }
}
