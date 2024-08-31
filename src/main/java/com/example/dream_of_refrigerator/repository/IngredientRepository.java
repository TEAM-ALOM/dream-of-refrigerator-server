package com.example.dream_of_refrigerator.repository;

import com.example.dream_of_refrigerator.domain.ingredient.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient,String> {
    List<Ingredient> findByIngredientCategory_Id(Long categoryId);

}