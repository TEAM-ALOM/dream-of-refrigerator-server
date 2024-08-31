package com.example.dream_of_refrigerator.ingredient.controller;

import com.example.dream_of_refrigerator.ingredient.domain.ingredient.Ingredient;
import com.example.dream_of_refrigerator.ingredient.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController  //REST API 용 컨트롤러! 데이터(JSON)를 반환
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;
    //모든 재료 조회(Ingredient table에 저장된 모든 것 전체 조회)
    @GetMapping//기본은 '전체'카테고리로, DB에 등록되어있는 전체 재료 조회
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        List<Ingredient> ingredients = ingredientService.findAll();
        return ResponseEntity.ok(ingredients);
    }
    //카테고리별 재료 조회(Ingredient table에서 ingredientCategory가 같은 것끼리 조회)
    @GetMapping("/category/{categoryId}")//category/1  category/2 ...
    public ResponseEntity<List<Ingredient>> getIngredientsByCategory(@PathVariable Long categoryId) {
        List<Ingredient> ingredients = ingredientService.findByCategory(categoryId);
        return ResponseEntity.ok(ingredients);
    }

}
