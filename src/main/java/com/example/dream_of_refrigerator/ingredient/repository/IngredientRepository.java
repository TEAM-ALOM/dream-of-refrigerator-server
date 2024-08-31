package com.example.dream_of_refrigerator.ingredient.repository;

import com.example.dream_of_refrigerator.ingredient.domain.ingredient.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient,String> {
    List<Ingredient> findByIngredientCategory_Id(Long categoryId);

    // UUID로 재료 조회
    List<Ingredient> findByUuid(String uuid);
}