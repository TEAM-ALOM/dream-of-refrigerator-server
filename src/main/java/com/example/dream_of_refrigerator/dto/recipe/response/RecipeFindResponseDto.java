package com.example.dream_of_refrigerator.dto.recipe.response;

import com.example.dream_of_refrigerator.domain.recipe.Recipe;
import com.example.dream_of_refrigerator.dto.ingredient.response.IngredientFindResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class RecipeFindResponseDto {
    private Long id;
    private String title;
    private String category;
    private String thumbnail;
    private List<IngredientFindResponseDto> ingredients;
    public RecipeFindResponseDto(Recipe recipe){
        this.id = recipe.getId();
        this.title = recipe.getTitle();
        this.category = recipe.getCategory();
        this.thumbnail = recipe.getThumbnail();
    }
}
