package com.letiproject.foodplanner.app.service;

/*
 * Created by @belrbeZ on 6.04.2017.
 */


import com.letiproject.foodplanner.app.postgres.model.User;
import com.letiproject.foodplanner.app.web.dto.UserDTO;

import java.util.Optional;

/**
 * Default Comment
 */
public interface IUserService extends IModelDTOService<User, UserDTO> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<User> getAuthorized();

    Optional<User> getByEmail(String email);

    Optional<User> getByPhone(String phone);

    /**
     * TRANSACTIONAL
     */
    Optional<User> removeByEmail(String email);

    /**
     * TRANSACTIONAL
     */
    Optional<User> removeByPhone(String phone);

    User findByEmail(String email);

    Optional<User> save(User user);
}
