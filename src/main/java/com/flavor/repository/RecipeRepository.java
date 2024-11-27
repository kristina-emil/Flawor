package com.flavor.repository;

import com.flavor.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.domain.Pageable;


public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByOrderByIdAsc(Pageable pageable);
}
