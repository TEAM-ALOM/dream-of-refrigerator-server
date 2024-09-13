package com.example.dream_of_refrigerator.repository;

import com.example.dream_of_refrigerator.domain.ingredient.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient,String>, JpaSpecificationExecutor<Ingredient> {
//    List<Ingredient> findByIngredientCategory_Id(Long categoryId);

    // UUID로 재료 조회
  //  List<Ingredient> findByUuid(String uuid);
}