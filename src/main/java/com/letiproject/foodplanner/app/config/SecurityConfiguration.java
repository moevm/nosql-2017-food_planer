package com.letiproject.foodplanner.app.config;

import com.letiproject.foodplanner.app.postgres.model.type.UserType;
import com.letiproject.foodplanner.app.web.util.resolvers.WebResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebMvcSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final String USER = UserType.USER.toString();
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
                        WebResolver.MENU,
                        WebResolver.LOGIN_REGISTER,
                        WebResolver.REGISTER, "/css/**", "/image/**", "/static/css/**", "/js/**", "/static/js/**", "/webjars/bootstrap/**", "/fonts/**").permitAll()
                .antMatchers(WebResolver.SECURED + "/**").hasAuthority(USER)
                .antMatchers(WebResolver.ADMIN + "/**").hasAuthority(USER)
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(WebResolver.LOGIN_REGISTER)
                .defaultSuccessUrl(WebResolver.PROFILE)
                .failureUrl(WebResolver.LOGIN_REGISTER + "?error=true")
//                    .usernameParameter("email")
//                    .passwordParameter("password")
//                    .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutUrl(WebResolver.LOGOUT)
//                    .deleteCookies("remember-me")
                .logoutSuccessUrl(WebResolver.MENU_FORM)
//                    .permitAll()
//                .and()
//                    .rememberMe()/*.rememberMeParameter("remember-me")*/.tokenValiditySeconds(86400)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(2).sessionRegistry(sessionRegistry())
                .and()
                .sessionFixation()
                .none()
                /*.and()
                    .sessionRegistry(sessionRegistry())
                    .sessionFixation().none()
                    .invalidSessionUrl("/invalidSession.html")*/
                .and()
                .csrf()
                .disable();
//                .addFilterAfter(
//                        new OauthProcessingFilter(), OauthProcessingFilter.class);;
//        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
//        SecurityContextHolder.setStrategyName( SecurityContextHolder.MODE_GLOBAL );
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

    /*@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }*/


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("user")
                .authorities(USER).and()
                .withUser("admin").password("admin")
                .authorities(ADMIN);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    /*@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService);
//                .passwordEncoder(new BCryptPasswordEncoder());
    }*/

    /*@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/image/**", "/static/image/**", "/static/css/**", "/static/js/**", "/images/**", "/webjars/bootstrap/**", "/fonts/**");
    }*/

}
