package com.example.demo.modules.common.services.Auth.Oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ClientResolver {
    @Autowired
    private ApplicationContext context;

    public OAuthClient resolve(String name) {
        switch (name){
            case "google":
                return context.getBean("google", GoogleOAuth.class);
            case "vk":
                return context.getBean("vk", VkOauth.class);
        }
        throw new IllegalArgumentException("Oauth client not found");
    }
}
