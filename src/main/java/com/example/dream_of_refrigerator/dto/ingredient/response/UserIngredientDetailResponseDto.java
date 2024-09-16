package com.example.dream_of_refrigerator.dto.ingredient.response;

import java.time.LocalDate;

public record UserIngredientDetailResponseDto (
        String name,
        String category,
        Integer quantity,
        LocalDate purchaseDate,
        LocalDate expiredDate,
        Boolean isFrozen,
        Boolean isRefrigerated)
{
}
