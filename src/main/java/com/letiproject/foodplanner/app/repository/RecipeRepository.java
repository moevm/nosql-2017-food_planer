package com.letiproject.foodplanner.app.repository;


import com.letiproject.foodplanner.app.domain.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeRepository extends MongoRepository<Recipe, Long> {
}
