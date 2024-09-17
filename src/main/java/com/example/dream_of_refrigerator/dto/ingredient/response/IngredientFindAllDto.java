package com.example.dream_of_refrigerator.dto.ingredient.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor //기본생성자
public class IngredientFindAllDto {
    @Schema(description = "재료의 고유 ID")
    private Long id;
    @Schema(description = "재료 이름")
    private String name;
    @Schema(description = "카테고리 이름")
    private String categoryName;
}