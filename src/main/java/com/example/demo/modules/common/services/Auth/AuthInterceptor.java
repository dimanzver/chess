package com.example.demo.modules.common.services.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Service
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private WebAuth webAuth;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!Objects.equals(request.getMethod(), "OPTIONS") && webAuth.authenticate() == null) {
            response.setStatus(401);
            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
