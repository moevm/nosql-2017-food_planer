package com.letiproject.foodplanner.app.controller;

import com.letiproject.foodplanner.app.domain.Ingredient;
import com.letiproject.foodplanner.app.domain.Recipe;
import com.letiproject.foodplanner.app.repository.RecipeRepository;
import com.letiproject.foodplanner.app.service.api.RecipeSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public String returnMainPage(Map<String, Object> model) {
        return "menuForm";
    }

    @RequestMapping(value = "/menu")
    public String returnMenuPage(HttpSession session) {
        if (session.getAttribute("menu") == null) {
            session.setAttribute("lastAttemptStatus", false);
            return "redirect:/";
        } else {
            session.setAttribute("lastAttemptStatus", true);
            return "menuFormTwo";
        }
    }

    @RequestMapping(value = "/menu/suggest", method = RequestMethod.POST)
    public String returnSuggestedMenu(HttpSession session,
                                      @RequestParam(name = "budgetLimit") String budgetLimit,
                                      @RequestParam(name = "caloriesLimit") String caloriesLimit,
                                      @RequestParam(name = "period") String period) {
        String[] caloriesBounds = caloriesLimit.split("-");
        String[] budgetBounds = budgetLimit.split("-");
        List<List<Recipe>> menu;
        try {
            menu = suggestionService.findBestSuggestion(Integer.parseInt(budgetBounds[0]), Integer.parseInt(budgetBounds[1]),
                                                        Integer.parseInt(caloriesBounds[0]), Integer.parseInt(caloriesBounds[1]));
            if (menu == null) {
                session.setAttribute("lastAttemptStatus", false);
                session.setAttribute("menu", null);
                session.setAttribute("totalCost", null);
                session.setAttribute("totalCalories", null);
                return "redirect:/";
            } else {
                session.setAttribute("lastAttemptStatus", true);
            }
        } catch (Exception e) {
            return "redirect:/";
        }
        session.setAttribute("menu", menu);
        session.setAttribute("totalCost", suggestionService.getTotalCostOfTheMenu(menu));
        session.setAttribute("totalCalories", suggestionService.getTotalCaloriesOfTheMenu(menu));
        return "redirect:/menu";
    }

    @RequestMapping(value = "/menu/{type}/{id}")
    @SuppressWarnings("unchecked")
    public String returnRecipeByTypeAndId(HttpSession session,
                                          Map<String, Object> model,
                                          @PathVariable(name = "type") String type,
                                          @PathVariable(name = "id") String id) {
        Object menuFromSession = session.getAttribute("menu");
        if (menuFromSession == null) return "redirect:/";
        List<List<Recipe>> menu = (List<List<Recipe>>) menuFromSession;
        int typeNumber = 0;
        if (type.equals("breakfast")) typeNumber = 0;
        else if (type.equals("lunch")) typeNumber = 1;
        else if (type.equals("dinner")) typeNumber = 2;
        Recipe currentRecipe = menu.get(Integer.parseInt(id) - 1).get(typeNumber);
        model.put("recipe", currentRecipe);

        return "recipes";
    }

    @RequestMapping(value = "/menu/ingredients")
    @SuppressWarnings("unchecked")
    public String ingredientsPage(HttpSession session, Map<String, Object> model) {
        List<List<Recipe>> menu = (List<List<Recipe>> )session.getAttribute("menu");
        Map<String, Ingredient.CommonIngredient> ingredientMap = suggestionService.getAggregatedIngredientsOfTheMenu(menu);
        model.put("ingredients", ingredientMap);
        return "shoppingList";
    }

}
