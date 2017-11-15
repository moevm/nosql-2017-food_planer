package com.letiproject.foodplanner.app.service.impl;

import com.letiproject.foodplanner.app.domain.Recipe;
import com.letiproject.foodplanner.app.repository.RecipeRepository;
import com.letiproject.foodplanner.app.service.api.RecipeSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

//imports as static

@Service
public class RecipeSuggestionServiceImpl implements RecipeSuggestionService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<List<Recipe>> findBestSuggestion(int budget, int caloriesLimit) {
        int budgetPerDay = budget/7;
        int budgetPerMeal = budgetPerDay/3;
        int caloriesPerDay = caloriesLimit/7;
        int caloriesPerMeal = caloriesPerDay/3;


        List<Recipe> breakfastsForWeek = getAnyThreePerMeal("завтрак", budgetPerMeal, caloriesPerMeal);
        List<Recipe> lunchesForWeek = getAnyThreePerMeal("обед", budgetPerMeal, caloriesPerMeal);
        List<Recipe> dinnersForWeek = getAnyThreePerMeal("ужин", budgetPerMeal, caloriesPerMeal);
        List<List<Recipe>> result = new ArrayList<>();
        result.add(breakfastsForWeek);
        result.add(lunchesForWeek);
        result.add(dinnersForWeek);

        return result;
    }


    private List<Recipe> getAnyThreePerMeal(String type, int budgetPerMeal, int caloriesPerMeal) {
        List<AggregationOperation> standartOperations = prepareAggregationOperationsList(budgetPerMeal, caloriesPerMeal, Sort.Direction.DESC, true);
        Aggregation agg = newAggregation(getAggregationOperationsByMealType(type, standartOperations));
        List<Recipe> result = new ArrayList<>();
        List<Recipe> recipes = mongoTemplate.aggregate(agg, Recipe.class, Recipe.class).getMappedResults();

        if (recipes.size() == 0) {
            standartOperations = prepareAggregationOperationsList(budgetPerMeal, caloriesPerMeal, Sort.Direction.ASC, false);
            agg = newAggregation(getAggregationOperationsByMealType(type, standartOperations));
            recipes = mongoTemplate.aggregate(agg, Recipe.class, Recipe.class).getMappedResults();
            if (recipes.size() < 7) {
                result.addAll(fillWeekMenuIfRecipesLessThan7(recipes));
            } else {
                result = recipes;
            }
        } else if (recipes.size() < 7 && recipes.size() > 0) {
            result.addAll(fillWeekMenuIfRecipesLessThan7(recipes));
        } else {
            result = recipes;
        }

        return result;
    }

    private List<Recipe> fillWeekMenuIfRecipesLessThan7 (List<Recipe> existedRecipes) {
        List<Recipe> filledRecipes = new ArrayList<>(7);
        int counter = 0;
        for (int i = 0; i < 7; i++) {
            if (counter < existedRecipes.size()) {
                filledRecipes.add(existedRecipes.get(counter++));
            } else {
                filledRecipes.add(existedRecipes.get(counter = 0));
            }
        }

        return filledRecipes;
    }

    private List<AggregationOperation> getAggregationOperationsByMealType (String type, List<AggregationOperation> standartOperations) {
        List<AggregationOperation> resultOperations = new ArrayList<>();
        resultOperations.addAll(standartOperations);
        resultOperations.add(new MatchOperation(Criteria.where("type").is(type)));
        resultOperations.add(new LimitOperation(7));
        return resultOperations;
    }

    private List<AggregationOperation> prepareAggregationOperationsList (int budget, int calories, Sort.Direction sortDirection, boolean isUpperLimit) {
        List<AggregationOperation> operations = new ArrayList<>();
        if (isUpperLimit) {
            operations.add(new MatchOperation(Criteria.where("calories").lte(calories)));
            operations.add(new MatchOperation(Criteria.where("cost").lte(budget)));
        } else {
            operations.add(new MatchOperation(Criteria.where("calories").gte(calories)));
            operations.add(new MatchOperation(Criteria.where("cost").gte(budget)));
        }
        operations.add(new SortOperation(new Sort(sortDirection, "calories")));
        return operations;
    }


}
