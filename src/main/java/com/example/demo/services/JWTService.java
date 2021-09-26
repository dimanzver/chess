package com.example.demo.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
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

    public Integer getUserId(String jwtToken) {
        try{
            JWTVerifier verifier = JWT.require(getAlgorithm())
                    .build();
            DecodedJWT jwt = verifier.verify(jwtToken);
            return Integer.parseInt(jwt.getClaim("uid").toString());
        }catch (JWTVerificationException e)  {
            return null;
        }
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(context.getEnvironment().getRequiredProperty("jwt.secret"));
    }

}
