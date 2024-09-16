package com.example.dream_of_refrigerator.repository.ingredient;

import com.example.dream_of_refrigerator.domain.ingredient.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Repository
public interface IngredientRepository extends JpaRepository<Ingredient,String>, JpaSpecificationExecutor<Ingredient> {
    List<Ingredient> findByCategory(String category);
    Optional<Ingredient> findById(Long id);
}