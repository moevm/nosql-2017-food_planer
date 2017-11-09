package com.letiproject.foodplanner.app.service.impl;

import com.letiproject.foodplanner.app.domain.Recipe;
import com.letiproject.foodplanner.app.repository.RecipeRepository;
import com.letiproject.foodplanner.app.service.api.RecipeSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeSuggestionServiceImpl implements RecipeSuggestionService {

    @Autowired
    RecipeRepository recipeRepository;

    @Override
    public List<Map<String, Recipe>> findBestSuggestion(int budget, int caloriesLimit) {
        List<Recipe> recipes = recipeRepository.findAll();
        int budgetPerDay = budget/7;
        int budgetPerMeal = budgetPerDay/3;
        int caloriesPerDay = caloriesLimit/7;
        int caloriesPerMeal = caloriesPerDay/3;
        List<Recipe> breakfasts = new ArrayList<>();
        List<Recipe> lunches = new ArrayList<>();
        List<Recipe> dinners = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getCost() <= budgetPerMeal
                    && recipe.getCalories() <= caloriesPerMeal
                    && recipe.getType().equalsIgnoreCase("завтрак")) {
                breakfasts.add(recipe);
            } else if (recipe.getCost() <= budgetPerMeal
                    && recipe.getCalories() <= caloriesPerMeal
                    && recipe.getType().equalsIgnoreCase("обед")) {
                lunches.add(recipe);
            } else if (recipe.getCost() <= budgetPerMeal
                    && recipe.getCalories() <= caloriesPerMeal
                    && recipe.getType().equalsIgnoreCase("ужин")) {
                dinners.add(recipe);
            }
        }
        Map<String, Recipe> breakfastsForWeek = getAnyThreePerMeal(breakfasts, "завтрак");
        Map<String, Recipe> lunchesForWeek = getAnyThreePerMeal(lunches, "обед");
        Map<String, Recipe> dinnersForWeek = getAnyThreePerMeal(dinners, "ужин");
        List<Map<String, Recipe>> result = new ArrayList<>();
        result.add(breakfastsForWeek);
        result.add(lunchesForWeek);
        result.add(dinnersForWeek);
        return result;
    }

    public Map<String, Recipe> getAnyThreePerMeal(List<Recipe> recipes, String type) {
        Map<String, Recipe> result = new HashMap<>();
        if (recipes.size() >= 7) {
            int step = recipes.size()/7;
            for (int i = 0, j = 0; j < 7; j++, i += step) {
                result.put(type + i + 1, recipes.get(i));
            }
        }
        if (recipes.size() < 7 && recipes.size() > 0) {
            int counter = 0;
            for (int i = 0; i < 7; i++) {
                if (counter < recipes.size()) {
                    result.put(type + i + 1, recipes.get(counter++));
                } else {
                    result.put(type + i + 1, recipes.get(counter = 0));
                }
            }
        }
        return result;
    }


}
