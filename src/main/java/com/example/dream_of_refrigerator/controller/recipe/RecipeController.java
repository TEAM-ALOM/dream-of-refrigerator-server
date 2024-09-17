package com.example.dream_of_refrigerator.controller.recipe;

import com.example.dream_of_refrigerator.dto.recipe.response.RecipeDetailFindResponseDto;
import com.example.dream_of_refrigerator.dto.recipe.response.RecipeFindResponseDto;

import com.example.dream_of_refrigerator.service.recipe.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/all/{page}")
    public ResponseEntity<List<RecipeFindResponseDto>> findAll(@PathVariable Integer page){
        return ResponseEntity.ok(recipeService.findAll(page));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<List<RecipeDetailFindResponseDto>> findDetail(@PathVariable Long id){
        return ResponseEntity.ok(recipeService.findRecipeDetail(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<RecipeFindResponseDto>> search(@RequestParam(name = "keyword") String keyword){
        return ResponseEntity.ok(recipeService.search(keyword));
    }

    @GetMapping("/recommend")
    public ResponseEntity<List<RecipeFindResponseDto>> recommend(){
        return ResponseEntity.ok(recipeService.findRecommendRecipe());
    }

    @GetMapping("/favorite/{page}")
    public ResponseEntity<List<RecipeFindResponseDto>> recommend(@PathVariable Integer page){
        return ResponseEntity.ok(recipeService.findFavorite(page));
    }

    @GetMapping("/category/{category}/{page}")
    public ResponseEntity<List<RecipeFindResponseDto>> findByCategory(@PathVariable Integer page,
                                                                      @PathVariable String category){
        return ResponseEntity.ok(recipeService.findByCategory(category, page));
    }

}
