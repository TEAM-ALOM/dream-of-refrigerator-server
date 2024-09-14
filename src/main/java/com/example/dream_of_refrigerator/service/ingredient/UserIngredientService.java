package com.example.dream_of_refrigerator.service.ingredient;

import com.example.dream_of_refrigerator.domain.ingredient.Ingredient;
import com.example.dream_of_refrigerator.domain.user.User;
import com.example.dream_of_refrigerator.domain.user.UserIngredient;
import com.example.dream_of_refrigerator.dto.ingredient.request.UserIngredientRequestDto;
import com.example.dream_of_refrigerator.dto.ingredient.response.UserIngredientDetailResponseDto;
import com.example.dream_of_refrigerator.dto.ingredient.response.UserIngredientResponseDto;
import com.example.dream_of_refrigerator.repository.ingredient.IngredientRepository;
import com.example.dream_of_refrigerator.repository.ingredient.UserIngredientRepository;
import com.example.dream_of_refrigerator.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserIngredientService {
    private final UserIngredientRepository userIngredientRepository;
    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;

    public UserIngredientService(UserIngredientRepository userIngredientRepository, UserRepository userRepository, IngredientRepository ingredientRepository) {
        this.userIngredientRepository = userIngredientRepository;
        this.userRepository = userRepository;
        this.ingredientRepository = ingredientRepository;
    }

    //사용자 냉장고에 있는 재료를 (expiration_date - purchase_date)의 차이가 짧은 순서로 정렬하여 조회
    public List<UserIngredientResponseDto> findAll(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
        return userIngredientRepository.findByUser(user).stream()
                .sorted(Comparator.comparingLong(userIngredient ->
                        Duration.between(userIngredient.getPurchaseDate().atStartOfDay(),
                                userIngredient.getExpirationDate().atStartOfDay()).toDays()))
                .map(userIngredient -> new UserIngredientResponseDto(
                        userIngredient.getIngredient().getName()))
                .collect(Collectors.toList());
    }

    public String findUserNickname(Long userId) {//{nickname}의 냉장고
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
        return user.getNickname();
    }

    public UserIngredientDetailResponseDto findDetail(Long userId, Long ingredientId) {
        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        // 사용자의 재료 조회
        UserIngredient userIngredient = userIngredientRepository.findByUserAndIngredientId(user, ingredientId)
                .orElseThrow(() -> new IllegalArgumentException("해당 재료를 찾을 수 없습니다."));

        // 재료 정보 가져오기
        Ingredient ingredient = userIngredient.getIngredient();

        // UserIngredientDetailResponseDTO 반환
        return new UserIngredientDetailResponseDto(
                ingredient.getName(),
                ingredient.getCategory(),
                userIngredient.getQuantity(),
                userIngredient.getPurchaseDate(),
                userIngredient.getExpirationDate(),
                ingredient.getIsFrozen(),
                ingredient.getIsRefrigerated()
        );

    }

//    public UserIngredient update(Long ingredientId, Long userIngredientId, UserIngredientRequestDto userIngredientRequestDto) {
//        // 수정하려는 userIngredient를 조회
//        UserIngredient userIngredient = userIngredientRepository.findById(userIngredientId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 재료 정보를 찾을 수 없습니다."));
//
//        // 재료 정보 수정 여부 확인
//        Ingredient registerIngredient = ingredientRepository.findById(ingredientId)
//        registerIngredient.setIsRefrigerated(userIngredientRequestDto.isRefrigerated());  // 냉장 여부 수정
//        registerIngredient.setIsFrozen(userIngredientRequestDto.isFrozen());  // 냉동 여부 수정
//
//        // UserIngredient 정보 수정
//        userIngredient.setIngredient(registerIngredient);  // 재료 수정
//        userIngredient.setQuantity(userIngredientRequestDto.quantity());  // 수량 수정
//        userIngredient.setExpirationDate(userIngredientRequestDto.expiredDate());  // 유통기한 수정
//
//        // 수정한 UserIngredient를 저장
//        return userIngredientRepository.save(userIngredient);
//    }
}
