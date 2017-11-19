package com.letiproject.foodplanner.app.web.util.validation.util.executors;

import org.springframework.stereotype.Component;

/**
 * Default Comment
 *
 * @author @belrbeZ
 * @since 12.05.2017
 */
@Component
public class PassValidator extends StringValidator {

    private static final Integer PASS_MIN_LENGTH = 6;

    @Override
    public boolean validate(String pass) {
        return (super.validate(pass) && pass.length() > PASS_MIN_LENGTH);
    }
}
