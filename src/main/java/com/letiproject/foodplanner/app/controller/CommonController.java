package com.letiproject.foodplanner.app.controller;

import com.letiproject.foodplanner.app.domain.Recipe;
import com.letiproject.foodplanner.app.repository.RecipeRepository;
import com.letiproject.foodplanner.app.service.api.RecipeSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class CommonController {

    @Autowired
    private RecipeRepository repository;

    @Autowired
    private RecipeSuggestionService suggestionService;

    @RequestMapping(value = "/")
    public String returnHelloWorldMsg(Map<String, Object> model) {

        List<Recipe> all = repository.findAll();
        List<List<Recipe>> menu = suggestionService.findBestSuggestion(10000, 10500);
        return "menuForm";
    }
}
