package com.letiproject.foodplanner.app.service.impl;

import com.letiproject.foodplanner.app.mongo.domain.Ingredient;
import com.letiproject.foodplanner.app.mongo.domain.Recipe;
import com.letiproject.foodplanner.app.mongo.repository.RecipeRepository;
import com.letiproject.foodplanner.app.service.api.RecipeSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

//imports as static

@Service
public class RecipeSuggestionServiceImpl implements RecipeSuggestionService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<List<Recipe>> findBestSuggestion(int budgetLowBound, int budgetUpperBound,
                                                 int caloriesLowBound, int caloriesUpperBound) {
        int budgetPerDayLowBound = budgetLowBound / 7;
        int budgetPerDayUpperBound = budgetUpperBound / 7;
        int budgetPerMealLowBound = budgetPerDayLowBound / 3;
        int budgetPerMealUpperBound = budgetPerDayUpperBound / 3;

        int caloriesPerDayLowBound = caloriesLowBound / 7;
        int caloriesPerDayUpperBound = caloriesUpperBound / 7;
        int caloriesPerMealLowBound = caloriesPerDayLowBound / 3;
        int caloriesPerMealUpperBound = caloriesPerDayUpperBound / 3;

        try {
            List<Recipe> breakfastsForWeek = getAnyThreePerMeal("завтрак", budgetPerMealLowBound, budgetPerMealUpperBound,
                    caloriesPerMealLowBound, caloriesPerMealUpperBound);
            List<Recipe> lunchesForWeek = getAnyThreePerMeal("обед", budgetPerMealLowBound, caloriesPerMealUpperBound,
                    caloriesPerMealLowBound, caloriesPerMealUpperBound);
            List<Recipe> dinnersForWeek = getAnyThreePerMeal("ужин", budgetPerMealLowBound, budgetPerMealUpperBound,
                    caloriesPerMealLowBound, caloriesPerMealUpperBound);

            List<List<Recipe>> result = new ArrayList<>();

            for (int i = 0; i < 7; i++) {
                result.add(new ArrayList<>(3));
                result.get(i).add(breakfastsForWeek.get(i));
                result.get(i).add(lunchesForWeek.get(i));
                result.get(i).add(dinnersForWeek.get(i));
            }

            return result;
        } catch (NullPointerException e) {
            return null;
        }
    }


    private List<Recipe> getAnyThreePerMeal(String type, int budgetPerMealLowBound, int budgetPerMealUpperBound,
                                            int caloriesPerMealLowBound, int caloriesPerMealUpperBound) {
        List<AggregationOperation> standardOperations = prepareAggregationOperationsList(budgetPerMealLowBound, budgetPerMealUpperBound,
                caloriesPerMealLowBound, caloriesPerMealUpperBound, Sort.Direction.DESC);
        Aggregation agg = newAggregation(getAggregationOperationsByMealType(type, standardOperations));
        List<Recipe> result = new ArrayList<>();
        List<Recipe> recipes = mongoTemplate.aggregate(agg, Recipe.class, Recipe.class).getMappedResults();

        if (recipes.size() > 0 && recipes.size() < 7) {
            result.addAll(fillWeekMenuIfRecipesLessThan7(recipes));
        } else if (recipes.size() == 0) {
            throw new NullPointerException();
        } else {
            result = recipes;
        }

        return result;
    }

    private List<Recipe> fillWeekMenuIfRecipesLessThan7(List<Recipe> existedRecipes) {
        List<Recipe> filledRecipes = new ArrayList<>(7);
        int counter = 0;
        for (int i = 0; i < 7; i++) {
            if (counter < existedRecipes.size()) {
                filledRecipes.add(existedRecipes.get(counter++));
            } else {
                filledRecipes.add(existedRecipes.get(counter = 0));
                counter++;
            }
        }

        return filledRecipes;
    }

    private List<AggregationOperation> getAggregationOperationsByMealType(String type, List<AggregationOperation> standardOperations) {
        List<AggregationOperation> resultOperations = new ArrayList<>();
        resultOperations.addAll(standardOperations);
        resultOperations.add(new MatchOperation(Criteria.where("type").is(type)));
        resultOperations.add(new LimitOperation(7));
        return resultOperations;
    }

    private List<AggregationOperation> prepareAggregationOperationsList(int budgetLowBound, int budgetUpperBound,
                                                                        int caloriesLowBound, int caloriesUpperBound,
                                                                        Sort.Direction sortDirection) {
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(new MatchOperation(Criteria.where("calories").lte(caloriesUpperBound).gte(caloriesLowBound)));
        operations.add(new MatchOperation(Criteria.where("cost").lte(budgetUpperBound).gte(budgetLowBound)));
        operations.add(new SortOperation(new Sort(sortDirection, "calories")));
        return operations;
    }

    public int getTotalCostOfTheMenu(List<List<Recipe>> menu) {
        int totalCost = 0;
        for (List<Recipe> day : menu) {
            for (Recipe recipe : day) {
                totalCost += recipe.getCost();
            }
        }
        return totalCost;
    }

    public int getTotalCaloriesOfTheMenu(List<List<Recipe>> menu) {
        int totalCalories = 0;
        for (List<Recipe> day : menu) {
            for (Recipe recipe : day) {
                totalCalories += recipe.getCalories();
            }
        }
        return totalCalories;
    }

    @Override
    public Map<String, Ingredient.CommonIngredient> getAggregatedIngredientsOfTheMenu(List<List<Recipe>> menu) {
        Map<String, Ingredient.CommonIngredient> ingredients = new HashMap<>();
        for (List<Recipe> dailyMenu : menu) {
            for (Recipe meal : dailyMenu) {
                for (Ingredient ingredient : meal.getIngredients()) {
                    if (ingredients.containsKey(ingredient.getName().toLowerCase())) {
                        Ingredient.CommonIngredient tmpIngredient = ingredients.get(ingredient.getName().toLowerCase());
                        tmpIngredient.setAmount(tmpIngredient.getAmount() + ingredient.getAmount());
                    } else {
                        ingredients.put(ingredient.getName().toLowerCase(),
                                new Ingredient.CommonIngredient(ingredient.getAmount(), ingredient.getUnits()));
                    }
                }
            }
        }
        return ingredients;
    }


}
