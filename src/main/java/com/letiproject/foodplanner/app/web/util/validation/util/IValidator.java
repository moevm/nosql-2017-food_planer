package com.letiproject.foodplanner.app.web.util.validation.util;

/**
 * Default Comment
 *
 * @author @belrbeZ
 * @since 12.05.2017
 */
public interface IValidator<T> {
    boolean validate(T t);
}
