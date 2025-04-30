package com.flavor.importer;

import com.flavor.dto.Meal;
import com.flavor.model.Category;
import com.flavor.model.Recipe;
import com.flavor.repository.CategoryRepository;
import org.springframework.stereotype.Component;

@Component
public class RecipeMapper {

    public Recipe map(Meal meal, CategoryRepository categoryRepository) {
        String categoryName = meal.getStrCategory();
        Category category = categoryRepository.findByName(categoryName);
    
        if (category == null) {
            category = categoryRepository.save(new Category(categoryName));
        }
    
        return new Recipe(
            meal.getStrMeal(),
            meal.getStrInstructions(),
            category,
            meal.getIdMeal(),  // внешний ID
            1                  // systemId = 1 (т.к. импорт из этого сервиса)
        );
    }    
    
}
