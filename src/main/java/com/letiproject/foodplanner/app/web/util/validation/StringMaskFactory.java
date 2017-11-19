package com.letiproject.foodplanner.app.web.util.validation;

/*
 * Created by belrbeZ on 19.03.2017.
 */

import org.hibernate.validator.constraints.Email;

import java.util.Arrays;

/**
 * Used to convert entities to JSON and other format representation
 */
public class StringMaskFactory {

    private static final String EMPTY_EMAIL = "";
    private static final Character MASK_SYMBOL = '*';
    private static final int MASK_POWER_INC = 0;

    /**
     * Mask email from like 'bob@mail.ru' to 'b**@***l.ru'
     *
     * @param email email to mask
     * @return masked email
     */
    public static String maskEmail(@Email String email) {
        int index = email.indexOf('@');
        return index == Validator.EMAIL_INCORRECT
                ? EMPTY_EMAIL
                : returnSymbolBack(index, maskStr(email, index));
    }

    /**
     * @return masked Email with symbol @ on its origin place
     */
    private static String returnSymbolBack(int position, String str) {
        char[] charArray = str.toCharArray();
        charArray[position] = '@';
        return new String(charArray);
    }

    public static String maskStr(String str) {
        return maskStr(str, 0);
    }

    public static String maskStr(String str, int desiredSpreadPoint) {
        return maskStr(str, desiredSpreadPoint, 0);
    }

    public static String maskStr(String str, int desiredSpreadPoint, int power) {
        return maskStr(str, desiredSpreadPoint, power, power);
    }

    /**
     * Convert ordinary string like 'hello' to masked one like 'he**o'
     *
     * @param str                string to mask
     * @param desiredSpreadPoint center point from where start masking
     * @param desiredPowerLeft   power to mask left side
     * @param desiredPowerRight  power to mask right side
     * @return masked string
     */
    public static String maskStr(String str,
                                 int desiredSpreadPoint,
                                 int desiredPowerLeft,
                                 int desiredPowerRight) {
        int length, spreadPoint;

        if (str == null || (length = str.length()) == 0)
            return null;

        StringBuilder maskedString = new StringBuilder(str);

        switch (length) {
            case 1:
                return MASK_SYMBOL.toString();

            case 2:
                char[] arr = str.toCharArray();
                arr[0] = MASK_SYMBOL;
                return Arrays.toString(arr);

            default:
                break;
        }

        // In case desiredSpreadPoint is too big or 0
        if ((spreadPoint = desiredSpreadPoint) == 0 || desiredSpreadPoint > length)
            spreadPoint = length / 2;

        // Calculate mask right end position
        int rightBorder = length - spreadPoint;
        int powerRight = (desiredPowerRight == 0 || desiredPowerRight > rightBorder)
                ? rightBorder / 2 - MASK_POWER_INC
                : desiredPowerRight;

        // Calculate mask left end position
        int leftBoarder = length - rightBorder;
        int powerLeft = (desiredPowerLeft == 0 || desiredPowerLeft > leftBoarder)
                ? leftBoarder / 2 - MASK_POWER_INC
                : desiredPowerLeft;

        char[] maskRight = new char[powerRight];
        char[] maskLeft = new char[powerLeft];

        Arrays.fill(maskRight, MASK_SYMBOL);
        Arrays.fill(maskLeft, MASK_SYMBOL);

        maskedString.replace(spreadPoint, spreadPoint + powerRight, String.valueOf(maskRight));
        maskedString.replace(spreadPoint - powerLeft, spreadPoint, String.valueOf(maskLeft));

        return maskedString.toString();
    }
}
