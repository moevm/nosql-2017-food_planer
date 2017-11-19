package com.letiproject.foodplanner.app.web.util.validation.annotation;

import com.letiproject.foodplanner.app.web.util.validation.annotation.impl.PassValidatorConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = PassValidatorConstraint.class)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Pass {
    String message() default "{Password is invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
