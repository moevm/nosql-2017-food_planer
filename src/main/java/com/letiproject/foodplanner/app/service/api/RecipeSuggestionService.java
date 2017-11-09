package com.letiproject.foodplanner.app.service.api;

import com.letiproject.foodplanner.app.domain.Recipe;

import java.util.List;
import java.util.Map;

public interface RecipeSuggestionService {
    List<Map<String, Recipe>> findBestSuggestion(int budget, int caloriesLimit);
}
