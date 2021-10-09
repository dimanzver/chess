package com.example.demo.services.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

abstract public class AbstractOAuthClient implements OAuthClient {
    @Autowired
    ApplicationContext context;

    abstract String getName();

    public String getCallbackUrl() {
        String host = context.getEnvironment().getRequiredProperty("host");
        return host + "/oauth-callback/" + getName();
    }
}
