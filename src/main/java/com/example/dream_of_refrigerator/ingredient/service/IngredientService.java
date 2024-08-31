package com.example.dream_of_refrigerator.ingredient.service;

import com.example.dream_of_refrigerator.ingredient.domain.ingredient.Ingredient;
import com.example.dream_of_refrigerator.ingredient.dto.BasicIngredientDto;
import com.example.dream_of_refrigerator.ingredient.dto.DetailIngredientDto;
import com.example.dream_of_refrigerator.ingredient.repository.IngredientRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    //Home, 재료 검색 페이지 등에선 재료 조회 시 재료이름,재료아이콘만 조회됨
    //// -> 모든 재료 조회 (기본 전체 조회)
    public List<BasicIngredientDto> findAllBasic() {
        return ingredientRepository.findAll().stream()
                .map(ingredient -> new BasicIngredientDto(ingredient.getName())).collect(Collectors.toList());
    }
    //// -> 카테고리별 재료 조회
    public List<BasicIngredientDto> findByCategoryBasic(Long categoryId) {
        return ingredientRepository.findByIngredientCategory_Id(categoryId).stream()
                .map(ingredient -> new BasicIngredientDto(ingredient.getName())).collect(Collectors.toList());
    }

    //재료 상세 조회 페이지에서는 상세정보까지 모두 조회됨
    //재료 이름,냉장보관(ON/OFF), 냉동보관(ON/OFF) 조회되도록
    // UUID로 재료 상세 조회
    public List<DetailIngredientDto> findAllDetails(String ingredientUuid) {
        return ingredientRepository.findByUuid(ingredientUuid).stream()
                .map(ingredient -> new DetailIngredientDto(
                        ingredient.getName(),
                        ingredient.getIngredientCategory() != null ? ingredient.getIngredientCategory().getName() : "Unknown",
                        ingredient.isRefrigerated(),
                        ingredient.isFrozen()
                ))
                .collect(Collectors.toList());
    }

}