package com.letiproject.foodplanner.app.security;

import com.letiproject.foodplanner.app.postgres.model.User;
import com.letiproject.foodplanner.app.postgres.model.UserRole;
import com.letiproject.foodplanner.app.service.impl.IUserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private IUserServiceImpl userService;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        logger.info("User : {}", user);

        if (user == null) {
            logger.info("User not found");
            throw new UsernameNotFoundException("Username not found");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                true, true, true, true, getGrantedAuthorities(user));
    }


    private Set<GrantedAuthority> getGrantedAuthorities(User user) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (UserRole role : user.getUserRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole().toString()));
        }
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getTypeOfUser().toString()));
        logger.info("authorities : {}", grantedAuthorities);

        return grantedAuthorities;
    }

}