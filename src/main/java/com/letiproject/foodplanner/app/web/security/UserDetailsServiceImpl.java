package com.letiproject.foodplanner.app.web.security;


import com.letiproject.foodplanner.app.postgres.model.User;
import com.letiproject.foodplanner.app.service.IUserService;
import com.letiproject.foodplanner.app.web.util.resolvers.SecureResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default Comment
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserService service;

    @Autowired
    public UserDetailsServiceImpl(IUserService service) {
        this.service = service;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = service.getByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user present with email: " + email));

        return new UserCredentials(user, SecureResolver.ROLE_USER);
    }

}
