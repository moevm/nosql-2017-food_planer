package com.letiproject.foodplanner.app.service.api;

import com.letiproject.foodplanner.app.domain.Recipe;

import java.util.List;

public interface RecipeSuggestionService {
    List<List<Recipe>> findBestSuggestion(int budget, int caloriesLimit);
}
