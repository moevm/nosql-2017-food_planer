package com.letiproject.foodplanner.app.web.security;

import com.letiproject.foodplanner.app.postgres.model.User;

public interface IUserDetailsService {
    User findByEmail(String email);
}
