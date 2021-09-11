package com.example.demo.modules.common.validators;

import com.example.demo.models.User;
import com.example.demo.modules.common.requests.RegisterRequestData;
import com.example.demo.repositories.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegisterRequestValidator implements Validator {

    private final UserRepository userRepository;

    public RegisterRequestValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return RegisterRequestData.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Введите имя");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Введите email");
        RegisterRequestData data = (RegisterRequestData) o;
        User existingUser = this.userRepository.findByEmail(data.getEmail());

        if(existingUser != null) {
            errors.rejectValue("email", "Данный E-mail уже занят");
        }
    }
}
