package com.letiproject.foodplanner.app.web.util.validation.util.executors;

import com.letiproject.foodplanner.app.postgres.model.type.UserType;
import com.letiproject.foodplanner.app.web.util.validation.util.IValidator;
import org.springframework.stereotype.Component;

/**
 * Default Comment
 *
 * @author @belrbeZ
 * @since 12.05.2017
 */
@Component
public class IdValidator implements IValidator<Long> {

    private static final Long ID_MIN_VALUE = UserType.EMPTY.getValue();

    @Override
    public boolean validate(Long id) {
        return (id != null && id > ID_MIN_VALUE);
    }
}
