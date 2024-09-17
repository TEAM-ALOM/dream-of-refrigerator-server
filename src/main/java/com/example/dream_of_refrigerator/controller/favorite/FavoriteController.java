package com.example.dream_of_refrigerator.controller.favorite;

import com.example.dream_of_refrigerator.dto.recipe.response.RecipeFindResponseDto;
import com.example.dream_of_refrigerator.service.favorite.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe/favorite")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PutMapping("/{recipeId}")
    public ResponseEntity<String> save(@PathVariable Long recipeId){
        return ResponseEntity.ok(favoriteService.save(recipeId));
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<String> delete(@PathVariable Long recipeId){
        return ResponseEntity.ok(favoriteService.delete(recipeId));
    }
}
