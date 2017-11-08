package com.letiproject.foodplanner.app.controller;

import com.letiproject.foodplanner.app.domain.Recipe;
import com.letiproject.foodplanner.app.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class CommonController {

    @Autowired
    private RecipeRepository repository;

    @RequestMapping(value = "/")
    public String returnHelloWorldMsg(Map<String, Object> model) {

        List<Recipe> all = repository.findAll();
        model.put("recipe", all.get(0));

        return "menuForm";
    }
}
