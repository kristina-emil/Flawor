package com.flavor.service;

import com.flavor.model.Recipe;
import com.flavor.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipe(Long id) {
        return recipeRepository.findById(id);
    }

    public Recipe addRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    /**
     * Возвращает список рецептов с использованием пагинации.
     */
    public List<Recipe> getRecipesWithLimitOffset(int offset, int limit) {
        int page = offset / limit; // Рассчитываем номер страницы.
        Pageable pageable = PageRequest.of(page, limit);
        return recipeRepository.findAllByOrderByIdAsc(pageable);
    }
}
