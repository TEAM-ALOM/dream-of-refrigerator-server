package com.example.dream_of_refrigerator.controller.ingredient;

import com.example.dream_of_refrigerator.domain.user.UserIngredient;
import com.example.dream_of_refrigerator.dto.ingredient.BasicIngredientDto;
import com.example.dream_of_refrigerator.dto.ingredient.request.UserIngredientRequestDto;
import com.example.dream_of_refrigerator.service.ingredient.IngredientService;
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

    //재료 등록(재료 검색 페이지에서 특정 재료 클릭할 경우 실행)
    @PostMapping("/{ingredientId}")
    public ResponseEntity<UserIngredientRequestDto> registerIngredients(@PathVariable("ingredientId") Long ingredientId,
                                                                        @RequestBody UserIngredientRequestDto userIngredientRequestDto){
        UserIngredient userIngredient=ingredientService.registerIngredients(ingredientId, userIngredientRequestDto);
        return  ResponseEntity.ok(userIngredient.toDto());
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
