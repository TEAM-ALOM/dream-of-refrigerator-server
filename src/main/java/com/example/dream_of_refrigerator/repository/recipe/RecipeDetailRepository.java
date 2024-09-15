package com.example.dream_of_refrigerator.repository.recipe;

import com.example.dream_of_refrigerator.domain.recipe.RecipeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeDetailRepository extends JpaRepository<RecipeDetail, Long> {
    List<RecipeDetail> findByRecipeId(@Param("recipe_id") Long recipeId);
}
