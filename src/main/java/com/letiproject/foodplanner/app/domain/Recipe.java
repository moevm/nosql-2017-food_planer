package com.letiproject.foodplanner.app.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "recipes")
public class Recipe {

    @Id
    private String id;
    private String name;
    private String cuisine;
    private String description;
    private int cost;
    private String cookingTime;
    private int calories;
    private String type;
    private List<Nutrition> nutrition;
    private List<Step> steps;
    private List<Ingredient> ingredients;

    public Recipe(String name, String cuisine, String description,
                  int cost, String cookingTime, int calories, String type,
                  List<Nutrition> nutrition, List<Step> steps, List<Ingredient> ingredients) {
        this.name = name;
        this.cuisine = cuisine;
        this.description = description;
        this.cost = cost;
        this.cookingTime = cookingTime;
        this.calories = calories;
        this.type = type;
        this.nutrition = nutrition;
        this.steps = steps;
        this.ingredients = ingredients;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public List<Nutrition> getNutrition() {
        return nutrition;
    }

    public void setNutrition(List<Nutrition> nutrition) {
        this.nutrition = nutrition;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
