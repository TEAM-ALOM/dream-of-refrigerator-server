package com.example.dream_of_refrigerator.controller;

import com.example.dream_of_refrigerator.dto.BasicIngredientDto;
import com.example.dream_of_refrigerator.dto.DetailIngredientDto;
import com.example.dream_of_refrigerator.service.IngredientService;
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
    @GetMapping//재료 name만 반환 (BasicIngredientDto)
    public ResponseEntity<List<BasicIngredientDto>> getAllIngredientsBasic() {
        List<BasicIngredientDto> ingredients = ingredientService.findAllBasic();
        return ResponseEntity.ok(ingredients);
    }

    //카테고리별 재료 조회
    @GetMapping("/category/{category}")//재료 name만 반환 (BasicIngredientDto)
    public ResponseEntity<List<BasicIngredientDto>> getIngredientsByCategory(@PathVariable String category) {
        List<BasicIngredientDto> ingredients = ingredientService.findByCategory(category);
        return ResponseEntity.ok(ingredients);
    }

    //재료 검색
    @GetMapping("/search")
    public ResponseEntity<List<BasicIngredientDto>> searchIngredients(@RequestParam String query) {
        List<BasicIngredientDto> ingredients = ingredientService.searchIngredients(query);
        return ResponseEntity.ok(ingredients);
    }

    // 재료 정보 상세조회
/*
    @GetMapping("/{ingredientUuid}")
    public ResponseEntity<List<DetailIngredientDto>> getIngredientDetailsByUuid(@PathVariable String ingredientUuid) {
        List<DetailIngredientDto> ingredients = ingredientService.findAllDetails(ingredientUuid);
        return ResponseEntity.ok(ingredients);
    }
*/

}
