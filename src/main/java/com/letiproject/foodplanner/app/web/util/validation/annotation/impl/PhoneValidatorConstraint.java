package com.letiproject.foodplanner.app.web.util.validation.annotation.impl;

import com.letiproject.foodplanner.app.web.util.validation.Validator;
import com.letiproject.foodplanner.app.web.util.validation.annotation.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Default Comment
 */
public class PhoneValidatorConstraint implements ConstraintValidator<Phone, String> {
    @Override
    public void initialize(Phone phone) {
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        return Validator.isPhoneValid(phone);
    }
}
