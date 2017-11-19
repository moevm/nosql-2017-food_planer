package com.letiproject.foodplanner.app.mongo.repository;


import com.letiproject.foodplanner.app.mongo.domain.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeRepository extends MongoRepository<Recipe, Long> {
}
