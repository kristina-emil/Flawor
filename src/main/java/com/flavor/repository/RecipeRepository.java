package com.flavor.repository;

import com.flavor.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByOrderByIdAsc(Pageable pageable);
}
