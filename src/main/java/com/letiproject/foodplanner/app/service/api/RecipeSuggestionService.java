package com.letiproject.foodplanner.app.service.api;

import com.letiproject.foodplanner.app.domain.Recipe;

import java.util.List;

public interface RecipeSuggestionService {
    List<List<Recipe>> findBestSuggestion(int budgetLowBound, int budgetUpperBound,
                                          int caloriesLowBound, int caloriesUpperBound);
    int getTotalCostOfTheMenu(List<List<Recipe>> menu);
    int getTotalCaloriesOfTheMenu(List<List<Recipe>> menu);
}
