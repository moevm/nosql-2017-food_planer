package com.letiproject.foodplanner.app.service.api;

import com.letiproject.foodplanner.app.domain.Ingredient;
import com.letiproject.foodplanner.app.domain.Recipe;

import java.util.List;
import java.util.Map;

public interface RecipeSuggestionService {
    List<List<Recipe>> findBestSuggestion(int budgetLowBound, int budgetUpperBound,
                                          int caloriesLowBound, int caloriesUpperBound);
    int getTotalCostOfTheMenu(List<List<Recipe>> menu);
    int getTotalCaloriesOfTheMenu(List<List<Recipe>> menu);
    Map<String, Ingredient.CommonIngredient> getAggregatedIngredientsOfTheMenu(List<List<Recipe>> menu);
}
