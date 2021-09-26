package com.example.demo.modules.common.controllers;

import com.example.demo.dto.ApiError;
import com.example.demo.models.User;
import com.example.demo.modules.common.requests.LoginRequestData;
import com.example.demo.modules.common.requests.RegisterRequestData;
import com.example.demo.modules.common.responses.UserAuthResponse;
import com.example.demo.modules.common.validators.RegisterRequestValidator;
import com.example.demo.services.UserService;
import com.example.demo.services.WebAuth;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AuthController {

    private final UserService userService;
    private final RegisterRequestValidator validator;
    private final WebAuth webAuth;

    public AuthController(UserService userService, RegisterRequestValidator validator, WebAuth webAuth) {
        this.userService = userService;
        this.validator = validator;
        this.webAuth = webAuth;
    }


    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestData requestData){
        DataBinder dataBinder = new DataBinder(requestData);
        dataBinder.addValidators(validator);
        dataBinder.validate();
        if(dataBinder.getBindingResult().hasErrors()) {
            List<ApiError> errors = new ArrayList<>();
            dataBinder.getBindingResult().getAllErrors().forEach(e -> {
                FieldError error = (FieldError) e;
                errors.add(new ApiError(error.getField(), error.getCode()));
            });
            return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User user = userService.register(requestData);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/auth/login")
    public UserAuthResponse login(@RequestBody LoginRequestData requestData, HttpServletResponse response) {
        UserAuthResponse userResponse = userService.login(requestData);
        if(userResponse.getUser() == null) {
            response.setStatus(401);
        }
        return userResponse;
    }

    @GetMapping("/auth/user")
    public ResponseEntity<?> user() {
        User user = this.webAuth.authenticate();
        if(user == null)
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
