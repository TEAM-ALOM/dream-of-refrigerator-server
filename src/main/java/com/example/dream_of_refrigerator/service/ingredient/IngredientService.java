package com.example.dream_of_refrigerator.service.ingredient;

import com.example.dream_of_refrigerator.domain.ingredient.Ingredient;
import com.example.dream_of_refrigerator.domain.user.User;
import com.example.dream_of_refrigerator.domain.user.UserIngredient;
import com.example.dream_of_refrigerator.dto.ingredient.response.BasicIngredientDto;
import com.example.dream_of_refrigerator.dto.ingredient.request.UserIngredientRequestDto;
import com.example.dream_of_refrigerator.dto.ingredient.response.IngredientFindAllDto;
import com.example.dream_of_refrigerator.global.util.JwtUtils;
import com.example.dream_of_refrigerator.repository.ingredient.IngredientRepository;

import com.example.dream_of_refrigerator.repository.ingredient.UserIngredientRepository;
import com.example.dream_of_refrigerator.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;
    private final UserIngredientRepository userIngredientRepository;


    // -> 모든 재료 조회 (기본 전체 조회)
    @Transactional(readOnly = true)
    public List<IngredientFindAllDto> findAllBasic() {
        return ingredientRepository.findAll().stream()
                .map(ingredient -> new IngredientFindAllDto(ingredient.getId(),ingredient.getName(),ingredient.getCategory())).collect(Collectors.toList());

    }
    // -> 카테고리별 재료 조회
    @Transactional(readOnly = true)
    public List<BasicIngredientDto> findByCategory(String category) {
        return ingredientRepository.findByCategory(category).
                stream().map(ingredient -> new BasicIngredientDto(ingredient.getId(),ingredient.getName())).collect(Collectors.toList());

    }

    //재료 검색 (검색한 단어 들어간 모든 재료)
    @Transactional(readOnly = true)
    public List<BasicIngredientDto> searchIngredients(String searchTerm) {
        if(searchTerm == null){
            throw new RuntimeException("키워드가 존재하지 않습니다.");
        }
        // Specification 객체 생성
        Specification<Ingredient> spec = (root, query, criteriaBuilder) -> {
            String likePattern = "%" + searchTerm + "%";
            return criteriaBuilder.like(root.get("name"), likePattern);
        };

        // 검색 조건에 따라 조회 및 DTO로 변환하여 반환
        return ingredientRepository.findAll(spec).stream()
                .map(ingredient -> new BasicIngredientDto(ingredient.getId(), ingredient.getName()))
                .collect(Collectors.toList());
    }

    // 재료 등록
    @Transactional
    public List<UserIngredient> register(List<UserIngredientRequestDto> userIngredientRequestDtos) {
        String email = JwtUtils.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("계정이 존재하지 않습니다."));
        List<UserIngredient> userIngredients = userIngredientRequestDtos.stream()
                .map(requestDto -> {
                    // 재료 찾기
                    Ingredient registerIngredient = ingredientRepository.findById(requestDto.ingredientId())
                            .orElseThrow(() -> new IllegalArgumentException("재료를 찾을 수 없습니다."));

                    // 중복 여부 확인
                    Optional<UserIngredient> existingIngredient = userIngredientRepository.findByUserAndIngredient(user, registerIngredient);
                    if (existingIngredient.isPresent()) {
                        // 이미 등록된 재료가 있을 경우, 해당 재료를 건너뜀
                        return null;
                    }

                    // 새로운 재료 등록
                    return new UserIngredient(registerIngredient, user, requestDto.quantity(),
                            requestDto.purchaseDate(), requestDto.expiredDate(),
                            requestDto.isRefrigerated(), requestDto.isFrozen());
                })
                .filter(Objects::nonNull) // null 값 제거 (이미 등록된 재료는 null로 반환되므로)
                .collect(Collectors.toList());

        return userIngredientRepository.saveAll(userIngredients);
    }

}