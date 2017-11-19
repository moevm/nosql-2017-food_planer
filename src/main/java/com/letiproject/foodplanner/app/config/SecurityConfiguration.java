package com.letiproject.foodplanner.app.config;

import com.letiproject.foodplanner.app.postgres.model.type.UserType;
import com.letiproject.foodplanner.app.web.util.resolvers.WebResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final String ROLE = UserType.USER.toString();
    private final String ADMIN = UserType.ADMIN.toString();

    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/",
                        WebResolver.WELCOME,
                        WebResolver.LOGIN,
                        WebResolver.MENU_FORM,
                        WebResolver.REGISTER, "/css/**", "/image/**", "/static/css/**", "/js/**", "/static/js/**", "/webjars/bootstrap/**", "/fonts/**").permitAll()
                .antMatchers(WebResolver.SECURED + "/**").hasAuthority(ROLE)
                .antMatchers(WebResolver.ADMIN + "/**").hasAuthority(ADMIN)
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(WebResolver.LOGIN)
                .defaultSuccessUrl(WebResolver.MENU_FORM)
                .failureUrl(WebResolver.LOGIN + "?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutUrl(WebResolver.LOGOUT)
                .logoutSuccessUrl(WebResolver.LOGIN)
                .deleteCookies("remember-me")
                .deleteCookies("JSESSIONID")
//                .and()
//                    .rememberMe().rememberMeParameter("remember-me").tokenRepository(tokenRepository).tokenValiditySeconds(86400)
                .and()
                .sessionManagement()
                .maximumSessions(1).sessionRegistry(sessionRegistry()).and()
                .sessionFixation().none()
                .invalidSessionUrl("/invalidSession.html")
                .and()
                .csrf().disable();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

/*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
*/

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

   /* @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
//            .getUserDetailsService().passwordEncoder(new BCryptPasswordEncoder());
    }*/

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/image/**", "/static/image/**", "/static/css/**", "/static/js/**", "/images/**", "/webjars/bootstrap/**", "/fonts/**");
    }

}
