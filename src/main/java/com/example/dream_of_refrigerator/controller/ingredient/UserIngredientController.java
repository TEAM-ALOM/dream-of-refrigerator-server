package com.example.dream_of_refrigerator.controller.ingredient;

import com.example.dream_of_refrigerator.dto.ingredient.request.UserIngredientRequestDto;
import com.example.dream_of_refrigerator.dto.ingredient.response.UserIngredientDetailResponseDto;
import com.example.dream_of_refrigerator.dto.ingredient.response.UserIngredientResponseDto;
import com.example.dream_of_refrigerator.service.ingredient.UserIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/refrigerator/{userId}")
@RequiredArgsConstructor
public class UserIngredientController {
    private final UserIngredientService userIngredientService;
    //{nickname}의 냉장고의 nickname조회
    @GetMapping("/nickname")
    public ResponseEntity<String> getUserNickname(@PathVariable("userId") Long userId) {
        String userNickname = userIngredientService.findUserNickname(userId);
        return ResponseEntity.ok(userNickname);
    }

    //냉장고에 있는 모든 재료 조회 (expiration_date - purchase_date)의 차이가 짧은 순서로 정렬하여 조회
    @GetMapping("/userIngredient")//정렬 기능 넣어야 함, 이름만 조회
    public ResponseEntity<List<UserIngredientResponseDto>> getAllUserIngredient(@PathVariable("userId")Long userId) {
        List<UserIngredientResponseDto> userIngredients = userIngredientService.findAll(userId);
        return ResponseEntity.ok(userIngredients);
    }

    //냉장고에 있는 특정 재료 상세 조회
    @GetMapping("userIngredient/{ingredientId}")
    public ResponseEntity<UserIngredientDetailResponseDto> getDetailUserIngredient(@PathVariable("userId")Long userId,
                                                                                    @PathVariable("ingredientId")Long ingredientId){
        UserIngredientDetailResponseDto detailIngredient=userIngredientService.findDetail(userId,ingredientId);
        return ResponseEntity.ok(detailIngredient);
    }
}
