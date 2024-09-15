package com.example.dream_of_refrigerator.service.ingredient;

import com.example.dream_of_refrigerator.domain.ingredient.Ingredient;
import com.example.dream_of_refrigerator.dto.ingredient.BasicIngredientDto;
import com.example.dream_of_refrigerator.dto.ingredient.DetailIngredientDto;
import com.example.dream_of_refrigerator.repository.ingredient.IngredientRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

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