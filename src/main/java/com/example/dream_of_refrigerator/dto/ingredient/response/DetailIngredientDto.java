package com.example.dream_of_refrigerator.dto.ingredient.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DetailIngredientDto {
    private String name;    //재료 이름
    private String category; // 카테고리 이름
    private boolean isRefrigerated; // 보관 방법
    private boolean isFrozen; // 냉동 여부
}
