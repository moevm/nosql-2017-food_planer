package com.letiproject.foodplanner.app.web.security;

/*
 * Created by @belrbeZ on 19.04.2017.
 */

import com.letiproject.foodplanner.app.service.IUserService;
import com.letiproject.foodplanner.app.web.dto.UserFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Default Comment
 */
//@Component
public class UserFormValidator implements Validator {

    private final IUserService userService;

    @Autowired
    public UserFormValidator(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserFormDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserFormDTO form = (UserFormDTO) target;
        validateUserCredentials(errors, form);
        validatePasswords(errors, form);
        validateEmail(errors, form);
    }

    public void validateUserCredentials(Errors errors, UserFormDTO form) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        if (form.getName().length() < 6 || form.getName().length() > 32)
            errors.rejectValue("name", "Name is too short or large");
    }

    private void validatePasswords(Errors errors, UserFormDTO form) {
        if (form.getPassword().length() < 6 || form.getPassword().length() > 32)
            errors.rejectValue("password", "Password is too short or large");

//        if (!form.getPassword().equals(form.getPasswordRepeted()))
//            errors.reject("password.no_match", "Passwords do not match");
    }

    private void validateEmail(Errors errors, UserFormDTO form) {
        if (userService.existsByEmail(form.getEmail()))
            errors.reject("email.exists", "User with this email already exists");
    }
}
