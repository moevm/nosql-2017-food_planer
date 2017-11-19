package com.letiproject.foodplanner.app.web.util.validation.annotation.impl;


import com.letiproject.foodplanner.app.web.util.validation.Validator;
import com.letiproject.foodplanner.app.web.util.validation.annotation.Pass;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PassValidatorConstraint implements ConstraintValidator<Pass, String> {

    @Override
    public void initialize(Pass phone) {
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        return Validator.isPhoneValid(phone);
    }
}
