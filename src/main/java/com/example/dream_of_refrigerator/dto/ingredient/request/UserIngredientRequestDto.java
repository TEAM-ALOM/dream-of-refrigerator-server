package com.example.dream_of_refrigerator.dto.ingredient.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record UserIngredientRequestDto(
        @Schema(description = "재료의 고유 ID")
        Long ingredientId,
        @Schema(description = "재료의 카테고리")
        String category,
        @Schema(description = "해당 재료의 보유량")
        Integer quantity,
        @Schema(description = "냉장고에 재료를 추가한 날짜")
        LocalDate purchaseDate,
        @Schema(description = "해당 재료의 유통기한")
        LocalDate expiredDate,
        @Schema(description = "냉동보관 여부",format = "boolean")
        Boolean isFrozen,
        @Schema(description = "냉장보관 여부",format = "boolean")
        Boolean isRefrigerated) {
}