package com.flavor.service;

import com.flavor.model.Recipe;
import com.flavor.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import com.flavor.model.Category;  
import com.flavor.repository.CategoryRepository;  


@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository; // Репозиторий для категорий

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;  // Внедряем репозиторий для категорий
    }

    public Recipe addRecipe(Recipe recipe) {
        // Проверяем, существует ли категория с указанным ID
        Long categoryId = recipe.getCategory().getId();
        Optional<Category> category = categoryRepository.findById(categoryId);

        if (!category.isPresent()) {
            throw new RuntimeException("Категория с таким ID не найдена");
        }

        // Устанавливаем категорию в рецепт
        recipe.setCategory(category.get());

        // Сохраняем рецепт
        return recipeRepository.save(recipe);
    }

    // Остальные методы без изменений
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipe(Long id) {
        return recipeRepository.findById(id);
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    public List<Recipe> getRecipesWithLimitOffset(int offset, int limit) {
        int page = offset / limit; 
        Pageable pageable = PageRequest.of(page, limit);
        return recipeRepository.findAllByOrderByIdAsc(pageable);
    }
}
