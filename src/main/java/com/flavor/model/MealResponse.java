package com.flavor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class MealResponse {
    @JsonProperty("meals")
    private List<Meal> meals;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}