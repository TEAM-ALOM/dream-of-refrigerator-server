package com.example.dream_of_refrigerator.dto.recipe.response;

import com.example.dream_of_refrigerator.domain.recipe.RecipeDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RecipeDetailFindResponseDto {
    private String description;
    private String image;

    public RecipeDetailFindResponseDto(RecipeDetail recipeDetail){
        this.description = recipeDetail.getDescription();
        this.image = recipeDetail.getImage();
    }
}
