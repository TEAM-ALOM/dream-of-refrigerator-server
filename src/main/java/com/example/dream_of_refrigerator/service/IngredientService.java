package com.example.dream_of_refrigerator.service;

import com.example.dream_of_refrigerator.domain.ingredient.Ingredient;
import com.example.dream_of_refrigerator.repository.IngredientRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    // 모든 재료 조회
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }
    // 카테고리별 재료 조회
    public List<Ingredient> findByCategory(Long categoryId) {
        return ingredientRepository.findByIngredientCategory_Id(categoryId);
    }
}