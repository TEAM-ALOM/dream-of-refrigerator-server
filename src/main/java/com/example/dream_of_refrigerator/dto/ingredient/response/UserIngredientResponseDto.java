package com.example.dream_of_refrigerator.dto.ingredient.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserIngredientResponseDto(
        @Schema(description = "재료 고유id")
        Long ingredientId,
        @Schema(description = "재료 이름")
        String ingredientName) {
}