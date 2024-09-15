package com.example.dream_of_refrigerator.dto.ingredient.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class IngredientFindResponseDto {
    private Long id;
    private String name;
    private String category;
    private Integer quantity;
    private LocalDate expirationDate;
    private LocalDate purchaseDate;
}
