package com.example.dream_of_refrigerator.dto.ingredient.request;

import java.time.LocalDate;

public record UserIngredientRequestDto(
        Long ingredientId,
        String category,
        Integer quantity,
        LocalDate purchaseDate,
        LocalDate expiredDate,
        Boolean isFrozen,
        Boolean isRefrigerated) {
}