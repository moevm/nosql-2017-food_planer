package com.letiproject.foodplanner.app.web.security;


import com.letiproject.foodplanner.app.postgres.model.User;
import com.letiproject.foodplanner.app.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class UserDetailsServiceImpl implements UserDetailsService, IUserDetailsService {

    private final IUserService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    public UserDetailsServiceImpl(IUserService service) {
        this.service = service;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LOGGER.info("Authenticating user with email={}", email.replaceFirst("@.*", "@***"));
        User user = service.getByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user present with email: " + email));

        return new UserCredentials(user, user.getType().toString());
    }

    public User findByEmail(String email) {
        return service.findByEmail(email);
    }

}
