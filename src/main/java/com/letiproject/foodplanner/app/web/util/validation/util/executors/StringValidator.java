package com.letiproject.foodplanner.app.web.util.validation.util.executors;


import com.letiproject.foodplanner.app.web.util.validation.util.IValidator;
import org.springframework.stereotype.Component;

/**
 * Default Comment
 *
 * @author @belrbeZ
 * @since 12.05.2017
 */
@Component
public class StringValidator implements IValidator<String> {

    @Override
    public boolean validate(String s) {
        return (s == null || s.trim().isEmpty());
    }
}
