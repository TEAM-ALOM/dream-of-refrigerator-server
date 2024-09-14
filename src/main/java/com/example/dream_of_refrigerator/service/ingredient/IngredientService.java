package com.example.dream_of_refrigerator.service.ingredient;

import com.example.dream_of_refrigerator.domain.ingredient.Ingredient;
import com.example.dream_of_refrigerator.domain.user.User;
import com.example.dream_of_refrigerator.domain.user.UserIngredient;
import com.example.dream_of_refrigerator.dto.ingredient.BasicIngredientDto;
import com.example.dream_of_refrigerator.dto.ingredient.DetailIngredientDto;
import com.example.dream_of_refrigerator.dto.ingredient.request.UserIngredientRequestDto;
import com.example.dream_of_refrigerator.repository.ingredient.IngredientRepository;

import com.example.dream_of_refrigerator.repository.ingredient.UserIngredientRepository;
import com.example.dream_of_refrigerator.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;
    private final UserIngredientRepository userIngredientRepository;

    // -> 모든 재료 조회 (기본 전체 조회)
    public List<BasicIngredientDto> findAllBasic() {
        return ingredientRepository.findAll().stream()
                .map(ingredient -> new BasicIngredientDto(ingredient.getName())).collect(Collectors.toList());
    }
    // -> 카테고리별 재료 조회
    public List<BasicIngredientDto> findByCategory(String category) {
        return ingredientRepository.findByCategory(category).
                stream().map(ingredient -> new BasicIngredientDto(ingredient.getName())).collect(Collectors.toList());
    }

    //재료 검색 (검색한 단어 들어간 모든 재료)
    public List<BasicIngredientDto> searchIngredients(String searchTerm) {
        // Specification 객체 생성
        Specification<Ingredient> spec = (root, query, criteriaBuilder) -> {
            String likePattern = "%" + searchTerm + "%";
            return criteriaBuilder.like(root.get("name"), likePattern);
        };

        // 검색 조건에 따라 조회 및 DTO로 변환하여 반환
        return ingredientRepository.findAll(spec).stream()
                .map(ingredient -> new BasicIngredientDto(ingredient.getName()))
                .collect(Collectors.toList());
    }
    public UserIngredient registerIngredients(Long ingredientId, UserIngredientRequestDto userIngredientRequestDto) {
        //재료를 등록하려고 하는 user
        User user = userRepository.findById(userIngredientRequestDto.UserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
        //등록하려는 ingredient
        Ingredient registerIngredient = ingredientRepository.findById(ingredientId);
        // 보관 방법 설정 (냉장,냉동)
        registerIngredient.setIsRefrigerated(userIngredientRequestDto.isRefrigerated());
        registerIngredient.setIsFrozen(userIngredientRequestDto.isFrozen());
        //등록하기
        UserIngredient userIngredient = UserIngredient.builder()
                .user(user)
                .ingredient(registerIngredient)
                .purchaseDate(LocalDate.now())  //추가 날짜를 구매 날짜로 설정
                .expirationDate(userIngredientRequestDto.expiredDate()) //유통기한 설정
                .quantity(userIngredientRequestDto.quantity()) //개수 설정
                .build();

        UserIngredient registeredIngredient = userIngredientRepository.save(userIngredient);
        return registeredIngredient;
    }

    //재료 상세 조회 페이지에서는 상세정보까지 모두 조회됨
    //재료 이름,냉장보관(ON/OFF), 냉동보관(ON/OFF) 조회되도록
    // UUID로 재료 상세 조회
    public List<DetailIngredientDto> findAllDetails(String ingredientUuid) {
//        return ingredientRepository.findByUuid(ingredientUuid).stream()
//                .map(ingredient -> new DetailIngredientDto(
//                        ingredient.getName(),
//                        ingredient.getIngredientCategory() != null ? ingredient.getIngredientCategory().getName() : "Unknown",
//                        ingredient.isRefrigerated(),
//                        ingredient.isFrozen()
//                ))
//                .collect(Collectors.toList());
        return null;
    }

}