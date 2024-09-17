package com.example.dream_of_refrigerator.controller.favorite;

import com.example.dream_of_refrigerator.dto.recipe.response.RecipeFindResponseDto;
import com.example.dream_of_refrigerator.service.favorite.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe/favorite")
@RequiredArgsConstructor
@Tag(name = "레시피 즐겨찾기 관련 api")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PutMapping("/{recipeId}")
    @Operation(summary = "레시피 즐겨찾기")
    public ResponseEntity<String> save(@PathVariable Long recipeId){
        return ResponseEntity.ok(favoriteService.save(recipeId));
    }

    @DeleteMapping("/{recipeId}")
    @Operation(summary = "레시피 삭제")
    public ResponseEntity<String> delete(@PathVariable Long recipeId){
        return ResponseEntity.ok(favoriteService.delete(recipeId));
    }
}
