package com.example.dream_of_refrigerator.dto.ingredient.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor //기본생성자
public class BasicIngredientDto {
    private Long id;
    private String name;
}
