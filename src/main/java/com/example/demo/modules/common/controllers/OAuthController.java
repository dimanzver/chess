package com.example.demo.modules.common.controllers;

import com.example.demo.dto.OAuthDto;
import com.example.demo.models.User;
import com.example.demo.modules.common.responses.UserAuthResponse;
import com.example.demo.repositories.UserRepository;
import com.example.demo.modules.common.services.Auth.JWTService;
import com.example.demo.modules.common.services.UserService;
import com.example.demo.modules.common.services.Auth.Oauth2.ClientResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OAuthController {

    private final ClientResolver clientResolver;
    private final UserRepository userRepository;
    private final UserService userService;
    private final JWTService jwtService;

    public OAuthController(ClientResolver clientResolver, UserRepository userRepository, UserService userService, JWTService jwtService) {
        this.clientResolver = clientResolver;
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping("/oauth2/redirect/{clientName}")
    public ResponseEntity<?> redirect(@PathVariable String clientName) {
        String redirectUrl = clientResolver.resolve(clientName).getAuthUrl();
        return new ResponseEntity<>(new OAuthRedirectResponse(redirectUrl), HttpStatus.OK);
    }

    @GetMapping("/oauth2/auth/{clientName}")
    public ResponseEntity<?> auth(@RequestParam String code, @PathVariable String clientName) {
        OAuthDto authData = clientResolver.resolve(clientName).auth(code);
        UserAuthResponse response = new UserAuthResponse();

        if(authData != null) {
            User user = userRepository.findByEmail(authData.getEmail());
            if(user == null) {
                user = userService.register(authData.getName(), authData.getEmail());
            }

            if(user != null) {
                response.setUser(user);
                response.setToken(jwtService.generateUserToken(user.getId()));
            }
        }

        if(response.getUser() == null)
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private static class OAuthRedirectResponse{
        private final String url;

        public OAuthRedirectResponse(String url) {
            this.url = url;
        }

        public String getUrl() {
            return this.url;
        }
    }

}
