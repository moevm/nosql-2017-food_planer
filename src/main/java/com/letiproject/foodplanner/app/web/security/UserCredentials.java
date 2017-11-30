package com.letiproject.foodplanner.app.web.security;


import com.letiproject.foodplanner.app.postgres.model.User;
import com.letiproject.foodplanner.app.postgres.model.type.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Default Comment
 */
public class UserCredentials extends org.springframework.security.core.userdetails.User {

    private String email;
    private String password;
    private String role;
    private User user;


    public UserCredentials(User user, String role) {
        super(user.getEmail(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getType().toString())));
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = role;
        this.user = user;
    }

    /*@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }*/


    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<UserType> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList());
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return user.getId();
    }

    public UserType getRole() {
        return user.getType();
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "user=" + user +
                "} " + super.toString();
    }
}
