package com.example.dream_of_refrigerator.dto.ingredient.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DetailIngredientDto {
    @Schema(description = "재료 이름")
    private String name;    //재료 이름
    @Schema(description = "카테고리 이름")
    private String category; // 카테고리 이름
    @Schema(description = "냉장 여부")
    private boolean isRefrigerated; // 냉장 여부
    @Schema(description = "냉동 여부")
    private boolean isFrozen; // 냉동 여부
}
