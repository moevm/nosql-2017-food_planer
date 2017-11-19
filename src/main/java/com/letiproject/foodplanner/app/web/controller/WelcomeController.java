package com.letiproject.foodplanner.app.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class WelcomeController {
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
    @RequestMapping(value = "/login")
    public String login() {
        return "/login";
    }

    /**
     * Login form with error.
     */
    @RequestMapping("/login-error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

}
