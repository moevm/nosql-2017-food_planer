package com.letiproject.foodplanner.app.web.controller;

import com.letiproject.foodplanner.app.postgres.model.User;
import com.letiproject.foodplanner.app.service.IUserService;
import com.letiproject.foodplanner.app.web.dto.UserFormDTO;
import com.letiproject.foodplanner.app.web.util.resolvers.TemplateResolver;
import com.letiproject.foodplanner.app.web.util.resolvers.WebResolver;
import com.letiproject.foodplanner.app.web.util.validation.ModelTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class SecureWebController {

    private static final Logger logger = LoggerFactory.getLogger(SecureWebController.class);

    private final IUserService userService;

    private final AuthenticationManager authenticationManager;

//    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public SecureWebController(IUserService userService, AuthenticationManager authenticationManager/*, AuthenticationProvider authenticationProvider*/) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
//        this.authenticationProvider = authenticationProvider;
    }

    private void authenticateUserAndSetSession(User user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), new ArrayList<>());

        // generate session if one doesn't exist
        request.getSession();
        HttpSession session = request.getSession(true);

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);
//        Authentication authenticatedUser = authenticationProvider.authenticate(token);
        logger.info("Logging in with [{}]", authenticatedUser.getPrincipal());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authenticatedUser);
        // Create a new session and add the security context.
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    }

    @RequestMapping(value = WebResolver.LOGIN_REGISTER, method = RequestMethod.GET)
    public ModelAndView loginGetComplex() {

        ModelAndView modelAndView = new ModelAndView(TemplateResolver.LOGIN_REGISTER);

        modelAndView.addObject("user", new UserFormDTO());

        return modelAndView;
    }



    @RequestMapping(value = WebResolver.LOGIN, method = RequestMethod.GET)
    public ModelAndView loginGet() {
        return new ModelAndView(TemplateResolver.LOGIN);
    }

    @RequestMapping(value = WebResolver.LOGOUT, method = RequestMethod.GET)
    public ModelAndView logoutGet(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.redirect(TemplateResolver.LOGIN_REGISTER + "?logout=true"));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null)
            new SecurityContextLogoutHandler().logout(request, response, auth);

        return modelAndView;
    }

    @RequestMapping(value = WebResolver.REGISTER, method = RequestMethod.GET)
    public ModelAndView registrationGet() {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.REGISTER);

        modelAndView.addObject("user", new UserFormDTO());

        return modelAndView;
    }

    @RequestMapping(value = WebResolver.REGISTER, method = RequestMethod.POST)
    public ModelAndView registrationPost(@Valid UserFormDTO user, BindingResult bindingResult, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView(TemplateResolver.redirect(WebResolver.MENU_FORM));

        if (userService.existsByEmail(user.getEmail())) {
            bindingResult.rejectValue("email", "error.user",
                    "There is already a user registered with the email provided");
            modelAndView.setViewName(TemplateResolver.LOGIN_REGISTER);
        }

        if (!bindingResult.hasErrors()) {
            try {
                Optional<User> usr = userService.save(ModelTranslator.toDAO(user));

                if (!usr.isPresent())
                    throw new NullPointerException();

                authenticateUserAndSetSession(usr.get(), request);
            } catch (Exception e) {
                e.printStackTrace();
                modelAndView.addObject("message", "Error");
            }
        } else {
            return new ModelAndView(TemplateResolver.redirect(WebResolver.LOGIN_REGISTER));
        }

        return modelAndView;
    }
}
