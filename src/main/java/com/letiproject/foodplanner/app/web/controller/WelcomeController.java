package com.letiproject.foodplanner.app.web.controller;

import com.letiproject.foodplanner.app.web.util.resolvers.TemplateResolver;
import com.letiproject.foodplanner.app.web.util.resolvers.WebResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {

    @RequestMapping(value = {"/", WebResolver.MENU_FORM}, method = RequestMethod.GET)
    public ModelAndView welcomeGet(Model model) {
        return new ModelAndView(TemplateResolver.MENU_FORM);
    }

    /**
     * Home page.
     */
    @RequestMapping("/home")
    public String home1() {
        return "/home";
    }

    @RequestMapping(value = "/about")
    public String about() {
        return "/about";
    }

    /**
     * Administration zone index.
     */
    @RequestMapping(value = "/admin")
    public String admin() {
        return "/admin";
    }

    /**
     * User zone index.
     */
    @RequestMapping(value = "/user")
    public String user() {
        return "/user";
    }

    /**
     * Error page.
     */
    @RequestMapping("/403.html")
    public String forbidden() {
        return "403";
    }


    /**
     * Login form.
     */
    /*@RequestMapping(value = "/login")
    public String login() {
        return "/login";
    }*/

    /**
     * Login form with error.
     */
    /*@RequestMapping("/login-error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }*/

}
