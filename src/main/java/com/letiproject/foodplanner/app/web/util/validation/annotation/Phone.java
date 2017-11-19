package com.letiproject.foodplanner.app.web.util.validation.annotation;

import com.letiproject.foodplanner.app.web.util.validation.annotation.impl.PhoneValidatorConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = PhoneValidatorConstraint.class)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {
    String message() default "{Phone is invalid!}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
