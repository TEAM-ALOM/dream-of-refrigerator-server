package com.example.dream_of_refrigerator.repository.recipe;

import com.example.dream_of_refrigerator.domain.recipe.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("select r from recipe r " +
            "left join fetch r.ingredientRecipes ir " +
            "left join fetch ir.ingredient i " +
            "where r.title like %:keyword% or r.category like %:keyword%")
    List<Recipe> search(@Param("keyword") String keyword);

    @Query("select r from recipe r " +
            "left join fetch r.ingredientRecipes ir " +
            "left join fetch ir.ingredient i ")
    Page<Recipe> findAll(Pageable pageable);
    @Query("select r from recipe r " +
            "left join fetch r.ingredientRecipes ir " +
            "left join fetch ir.ingredient i ")
    List<Recipe> findAll();

    @Query("select r from recipe r " +
            "left join fetch r.ingredientRecipes ir " +
            "left join fetch ir.ingredient i " +
            "where r.category=:category")
    Page<Recipe> findByCategory(Pageable pageable, @Param("category") String category);
    @Query("select r from recipe r " +
            "left join fetch r.ingredientRecipes ir " +
            "left join fetch ir.ingredient i " +
            "left join fetch r.favorites f " +
            "left join fetch f.user u "+
            "where u.email=:email")
    Page<Recipe> findFavorite(Pageable pageable, @Param("email") String email);
    @Query("select r from recipe r " +
            "left join fetch r.ingredientRecipes ir " +
            "left join fetch ir.ingredient i " +
            "left join fetch i.userIngredients ui " +
            "left join fetch ui.user u " +
            "where u.email=:email and i.id in (select i2.id from ui.ingredient i2) ")
    List<Recipe> findRecommendRecipes(@Param("email") String email);
}
