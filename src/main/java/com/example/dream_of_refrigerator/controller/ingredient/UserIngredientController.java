package com.example.dream_of_refrigerator.controller.ingredient;

import com.example.dream_of_refrigerator.dto.ingredient.request.UserIngredientRequestDto;
import com.example.dream_of_refrigerator.dto.ingredient.response.UserIngredientDetailResponseDto;
import com.example.dream_of_refrigerator.dto.ingredient.response.UserIngredientResponseDto;
import com.example.dream_of_refrigerator.service.ingredient.UserIngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/refrigerator")
@RequiredArgsConstructor
@Tag(name = "냉장고(홈) API", description = "사용자의 냉장고(홈)과 관련된 API입니다.")
public class UserIngredientController {
    private final UserIngredientService userIngredientService;

    @Operation(summary = "사용자가 냉장고에 등록한 재료 전체 조회", description = "(유통기한-현재날짜)의 차이가 짧은 순서로 정렬하여 조회합니다.")
    @ApiResponse(responseCode = "200", description = "사용자의 냉장고에 등록된 모든 재료 조회 성공")
    @GetMapping("/userIngredient")
    public ResponseEntity<List<UserIngredientResponseDto>> getAllUserIngredient() {
        List<UserIngredientResponseDto> userIngredients = userIngredientService.findAll();
        return ResponseEntity.ok(userIngredients);
    }

    //냉장고에 있는 특정 재료 상세 조회
    @Operation(summary = "사용자가 냉장고에 등록한 재료 정보 상세 조회", description = "재료 이름,카테고리,보유수량,구매날짜,유통기한,냉장보관,냉동보관 에 대헤 조회할 수 있습니다.")
    @ApiResponse(responseCode = "200", description = "사용자의 냉장고에 등록된 재료 상세 조회 성공")
    @GetMapping("userIngredient/{ingredientId}")
    public ResponseEntity<UserIngredientDetailResponseDto> getDetailUserIngredient(
            @Schema(description = "재료고유id",example = "11")
            @PathVariable("ingredientId")Long ingredientId){
        UserIngredientDetailResponseDto detailIngredient=userIngredientService.findDetail(ingredientId);
        return ResponseEntity.ok(detailIngredient);
    }

    // 냉장고에 있는 특정 재료 정보 수정
    @Operation(summary = "냉장고에 등록한 재료 정보 수정", description = "냉장고에 등록한 특정 재료를 선택한 후, 상세 정보를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "냉장고에 등록한 재료 정보 수정 성공")
    @PatchMapping("userIngredient/{ingredientId}")
    public ResponseEntity<UserIngredientDetailResponseDto> editUserIngredient(
            @Schema(description = "재료고유id",example = "11")
            @PathVariable("ingredientId") Long ingredientId,
            @RequestBody UserIngredientRequestDto requestDto) {
        UserIngredientDetailResponseDto detailIngredient = userIngredientService.edit(ingredientId, requestDto);
        return ResponseEntity.ok(detailIngredient);
    }
    // 냉장고에 있는 특정 재료 삭제
    @Operation(summary = "냉장고에 등록한 재료를 삭제합니다.", description = "냉장고에 등록한 특정 재료를 선택한 후, 해당 재료를 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "냉장고에 등록한 재료 삭제 성공")
    @DeleteMapping("userIngredient/{ingredientId}")
    public ResponseEntity<Void> deleteUserIngredient(
            @Schema(description = "재료고유id",example = "11")
            @PathVariable("ingredientId") Long ingredientId) {
        userIngredientService.delete(ingredientId);
        return ResponseEntity.noContent().build();  // 성공적으로 삭제된 경우 204 응답
    }
}
