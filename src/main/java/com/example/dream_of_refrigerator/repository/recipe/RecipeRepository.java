package com.example.dream_of_refrigerator.repository.recipe;

import com.example.dream_of_refrigerator.domain.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("select r from recipe r where r.title like %:keyword% or r.category like %:keyword%")
    List<Recipe> search(@Param("keyword") String keyword);

    @Query("select r from recipe r " +
            "left join fetch r.ingredientRecipes ir " +
            "left join fetch ir.ingredient i " +
            "left join fetch i.userIngredients ui " +
            "left join fetch ui.user u " +
            "where u.email=:email and i.id in (select i2.id from ui.ingredient i2) " +
            "order by ui.expirationDate ")
    List<Recipe> findRecommendRecipes(@Param("email") String email);
}
