package com.letiproject.foodplanner.app.web.util.validation;

/*
 * Created by @belrbeZ on 16.04.2017.
 */

import java.util.function.Predicate;

/**
 * Default Comment
 */
public class Validator {

    // VALIDATOR MULTIPLE CLASSES
    static final int EMAIL_INCORRECT = -1;
    private static Predicate<String> phoneFilter = (Validator::isPhoneValid);

    public static boolean isIdValid(Long id) {
        return id != null && id > 0;
    }

    public static boolean isStrEmpty(String str) {
        return (str == null || str.trim().isEmpty());
    }

    public static boolean isPassValid(String pass) {
        return (pass != null && pass.trim().isEmpty() && pass.length() > 6);
    }

    public static boolean isEmailValid(String email) {
        return (email != null && !email.trim().isEmpty() && email.length() > 3 && email.indexOf('@') != EMAIL_INCORRECT);
    }

    public static int isEmailValidReturnIndex(String email) {
        return (email != null && !email.trim().isEmpty() && email.length() > 3) ? email.indexOf('@') : EMAIL_INCORRECT;
    }

    public static boolean isPhoneValid(String phone) {
        return !(phone == null || phone.isEmpty())
                //validate phone numbers of format "1234567890"
                && (phone.matches("\\d{10}")
                //validating phone number with -, . or spaces
                || phone.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")
                //validating phone number with extension length from 3 to 5
                || phone.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")
                //validating phone number where area code is in braces ()
                || phone.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}"));

    }
}
