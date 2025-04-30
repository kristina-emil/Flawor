package com.flavor.dto; // ✅ Соответствует структуре

import java.util.List;

public class MealDto {
    private List<Meal> meals;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
