package com.flavor.flavor.repository;

import com.flavor.flavor.model.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByOrderByIdAsc(Pageable pageable);
}
