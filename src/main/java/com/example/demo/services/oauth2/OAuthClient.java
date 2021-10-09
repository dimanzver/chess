package com.example.demo.services.oauth2;

import com.example.demo.dto.OAuthDto;

public interface OAuthClient {
    String getAuthUrl();
    OAuthDto auth(String code);
}
