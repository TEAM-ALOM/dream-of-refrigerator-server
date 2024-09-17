package com.example.dream_of_refrigerator.dto.ingredient.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
@Setter
public class IngredientFindResponseDto {
    @Schema(description = "재료 고유id")
    private Long id;
    @Schema(description = "재료 이름")
    private String name;
    @Schema(description = "재료 카테고리")
    private String category;
    @Schema(description = "보유 수량")
    private Integer quantity;
    @Schema(description = "유통기한")
    private LocalDate expirationDate;
    @Schema(description = "구매날짜")
    private LocalDate purchaseDate;
    @Schema(description = "보유여부")
    private Boolean isContained;
}
