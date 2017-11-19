package com.letiproject.foodplanner.app.web.util.validation.util.executors;

import org.springframework.stereotype.Component;

/**
 * Default Comment
 *
 * @author @belrbeZ
 * @since 12.05.2017
 */
@Component
public class PhoneValidator extends StringValidator {

    @Override
    public boolean validate(String phone) {
        return (!super.validate(phone)
                //validate phone numbers of format "1234567890"
                && (phone.matches("\\d{10}")
                //validating phone number with -, . or spaces
                || phone.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")
                //validating phone number with extension length from 3 to 5
                || phone.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")
                //validating phone number where area code is in braces ()
                || phone.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")));
    }
}
