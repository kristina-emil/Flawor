package com.flavor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flavor.model.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
