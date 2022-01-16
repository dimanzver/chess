package com.example.demo.modules.common.services.oauth2;

import com.example.demo.dto.OAuthDto;

public interface OAuthClient {
    String getAuthUrl();
    OAuthDto auth(String code);
}
