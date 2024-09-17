package com.example.dream_of_refrigerator.dto.ingredient.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record UserIngredientDetailResponseDto (
        @Schema(description = "재료 이름")
        String name,
        @Schema(description = "재료가 속한 카테고리")
        String category,
        @Schema(description = "보유 재료 수량")
        Integer quantity,
        @Schema(description = "구매날짜")
        LocalDate purchaseDate,
        @Schema(description = "유통기한")
        LocalDate expiredDate,
        @Schema(description = "냉장보관여부")
        Boolean isFrozen,
        @Schema(description = "냉동보관여부")
        Boolean isRefrigerated)
{
}
