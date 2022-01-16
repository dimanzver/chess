package com.example.demo.modules.common.services.oauth2;

import com.example.demo.dto.OAuthDto;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;

@Service("google")
public class GoogleOAuth extends AbstractOAuthClient {
    @Override
    public String getAuthUrl() {
        Servlet servlet = new Servlet();
        try {
            return servlet.initializeFlow()
                    .newAuthorizationUrl()
                    .setRedirectUri(getCallbackUrl())
                    .toString();
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OAuthDto auth(String code) {
        HttpTransport httpTransport = new NetHttpTransport();

        try {
            GoogleTokenResponse response = new GoogleAuthorizationCodeTokenRequest(
                    httpTransport,
                    GsonFactory.getDefaultInstance(),
                    getClientId(),
                    getClientSecret(),
                    code,
                    getCallbackUrl()
            )
                .setScopes(Set.of("email", "profile"))
                .execute();

            GoogleIdToken.Payload payload = response.parseIdToken().getPayload();

            OAuthDto oAuthDto = new OAuthDto();
            oAuthDto.setEmail(payload.getEmail());
            oAuthDto.setName(payload.get("name").toString());
            oAuthDto.setPicture(payload.get("picture").toString());
            return oAuthDto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    String getName() {
        return "google";
    }

    private String getClientId() {
        return context.getEnvironment().getRequiredProperty("spring.security.oauth2.client.registration.google.clientId");
    }

    private String getClientSecret() {
        return context.getEnvironment().getRequiredProperty("spring.security.oauth2.client.registration.google.clientSecret");
    }


    private class Servlet extends AbstractAuthorizationCodeServlet{

        @Override
        protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException {
            return new GoogleAuthorizationCodeFlow.Builder(
                    new NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    getClientId(),
                    getClientSecret(),
                    Set.of("email", "profile")
            ).build();
        }

        @Override
        protected String getRedirectUri(HttpServletRequest httpServletRequest) {
            return getCallbackUrl();
        }

        @Override
        protected String getUserId(HttpServletRequest httpServletRequest) {
            return null;
        }
    }
}
