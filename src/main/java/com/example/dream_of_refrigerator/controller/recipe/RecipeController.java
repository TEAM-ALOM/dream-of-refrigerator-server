package com.example.dream_of_refrigerator.controller.recipe;

import com.example.dream_of_refrigerator.dto.recipe.response.RecipeDetailFindResponseDto;
import com.example.dream_of_refrigerator.dto.recipe.response.RecipeFindResponseDto;

import com.example.dream_of_refrigerator.service.recipe.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
@Tag(name = "레시피 관련 api")
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/all/{page}")
    @Operation(summary = "레시피 조회", description = "page 0부터 시작 10개씩 보여줍니다.")
    public ResponseEntity<List<RecipeFindResponseDto>> findAll(@PathVariable Integer page){
        return ResponseEntity.ok(recipeService.findAll(page));
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "레시피 디테일 조회", description = "find all에서 받은 id값으로 상세 조회")
    public ResponseEntity<List<RecipeDetailFindResponseDto>> findDetail(@PathVariable Long id){
        return ResponseEntity.ok(recipeService.findRecipeDetail(id));
    }

    @GetMapping("/search")
    @Operation(summary = "레시피 검색", description = "parameter keyword에 사용자 입력 문자열 받아서 검색")
    public ResponseEntity<List<RecipeFindResponseDto>> search(@RequestParam(name = "keyword", required = false) String keyword){
        return ResponseEntity.ok(recipeService.search(keyword));
    }

    @GetMapping("/recommend")
    @Operation(summary = "추천 레시피 조회", description = "사용자 기준으로 사용자가 보유하고 있는 재료를 가진 레시피를 보여줍니다.")
    public ResponseEntity<List<RecipeFindResponseDto>> findRecommendRecipe(){
        return ResponseEntity.ok(recipeService.findRecommendRecipe());
    }

    @GetMapping("/favorite/{page}")
    @Operation(summary = "즐겨찾기 레시피 조회", description = "사용자 기준으로 사용자가 즐겨찾기 선택한 레시피를 보여줍니다. </br> page 0부터 시작 10개씩 보여줍니다.")
    public ResponseEntity<List<RecipeFindResponseDto>> findFavoriteRecipe(@PathVariable Integer page){
        return ResponseEntity.ok(recipeService.findFavorite(page));
    }

    @GetMapping("/category/{category}/{page}")
    @Operation(summary = "레시피 카테고리로 조회", description = "카테고리로 레시피를 보여줍니다. </br> page 0부터 시작 10개씩 보여줍니다.")
    public ResponseEntity<List<RecipeFindResponseDto>> findByCategory(@PathVariable Integer page,
                                                                      @PathVariable String category){
        return ResponseEntity.ok(recipeService.findByCategory(category, page));
    }

    @GetMapping("/random")
    @Operation(summary = "랜덤 레시피 조회", description = "랜덤한 레시피 1개만 리턴합니다.")
    public ResponseEntity<RecipeFindResponseDto> findRandomRecipe(){
        return ResponseEntity.ok(recipeService.findRandomRecipe());
    }

}
