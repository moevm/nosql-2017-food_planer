package com.letiproject.foodplanner.app.util.type;

import org.springframework.security.core.GrantedAuthority;

public enum AuthorityType implements GrantedAuthority {
    ADMIN,
    HR,
    USER;

    @Override
    public String getAuthority() {
        return "ROLE_" + toString();
    }
}
