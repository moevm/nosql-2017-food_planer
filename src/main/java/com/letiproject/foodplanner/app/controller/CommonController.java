package com.letiproject.foodplanner.app.controller;

import com.letiproject.foodplanner.app.domain.Recipe;
import com.letiproject.foodplanner.app.repository.RecipeRepository;
import com.letiproject.foodplanner.app.service.api.RecipeSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
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
        return "menuForm";
    }

    @RequestMapping(value = "/menu")
    public String returnSuggestedMenu(HttpSession session,
                                      Map<String, Object> model,
                                      @RequestParam(name = "budgetLimit") String budgetLimit,
                                      @RequestParam(name = "caloriesLimit") String caloriesLimit,
                                      @RequestParam(name = "period") String period) {
        List<List<Recipe>> menu =
                suggestionService.findBestSuggestion(Integer.parseInt(budgetLimit), Integer.parseInt(caloriesLimit));
        session.setAttribute("currentMenu", menu);
        model.put("menu", menu);
        return "menuFormTwo";
    }

    @RequestMapping(value = "/menu/{type}/{id}")
    public String returnRecipeByTypeAndId(HttpSession session,
                                          Map<String, Object> model,
                                          @PathVariable(name = "type") String type,
                                          @PathVariable(name = "id") String id) {
        Object menuFromSession = session.getAttribute("currentMenu");
        if (menuFromSession == null) return "menuForm";
        List<List<Recipe>> menu = (List<List<Recipe>>) menuFromSession;
        int typeNumber = 0;
        if (type.equals("breakfast")) typeNumber = 0;
        else if (type.equals("lunch")) typeNumber = 1;
        else if (type.equals("dinner")) typeNumber = 2;
        Recipe currentRecipe = menu.get(Integer.parseInt(id) - 1).get(typeNumber);
        model.put("recipe", currentRecipe);

        return "recipes";
    }

}
