package com.example.demo.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class JWTService {

    @Autowired
    ApplicationContext context;

    public String generateUserToken(Integer userId) {
        return JWT.create()
                .withClaim("uid", userId)
                .sign(getAlgorithm());
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(context.getEnvironment().getRequiredProperty("jwt.secret"));
    }

}
