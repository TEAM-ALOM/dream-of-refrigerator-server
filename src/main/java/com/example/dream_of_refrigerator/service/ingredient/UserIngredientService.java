package com.example.dream_of_refrigerator.service.ingredient;

import com.example.dream_of_refrigerator.domain.ingredient.Ingredient;
import com.example.dream_of_refrigerator.domain.recipe.Recipe;
import com.example.dream_of_refrigerator.domain.user.User;
import com.example.dream_of_refrigerator.domain.user.UserIngredient;
import com.example.dream_of_refrigerator.dto.ingredient.request.UserIngredientRequestDto;
import com.example.dream_of_refrigerator.dto.ingredient.response.UserIngredientDetailResponseDto;
import com.example.dream_of_refrigerator.dto.ingredient.response.UserIngredientResponseDto;
import com.example.dream_of_refrigerator.global.util.JwtUtils;
import com.example.dream_of_refrigerator.repository.ingredient.IngredientRepository;
import com.example.dream_of_refrigerator.repository.ingredient.UserIngredientRepository;
import com.example.dream_of_refrigerator.repository.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserIngredientService {
    private final UserIngredientRepository userIngredientRepository;
    private final UserRepository userRepository;

    public UserIngredientService(UserIngredientRepository userIngredientRepository, UserRepository userRepository, IngredientRepository ingredientRepository) {
        this.userIngredientRepository = userIngredientRepository;
        this.userRepository = userRepository;
    }

    //사용자 냉장고에 있는 재료를 (expiration_date - purchase_date)의 차이가 짧은 순서로 정렬하여 조회
    @Transactional(readOnly = true)
    public List<UserIngredientResponseDto> findAll() {
        String email = JwtUtils.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
        LocalDate today = LocalDate.now();  // 현재 날짜

        return userIngredientRepository.findByUser(user).stream()
                // 현재 날짜와 유통기한의 차이를 계산하여 정렬
                .sorted(Comparator.comparingLong(userIngredient ->
                        ChronoUnit.DAYS.between(today, userIngredient.getExpirationDate())))
                .map(userIngredient -> new UserIngredientResponseDto(
                        userIngredient.getIngredient().getId(),
                        userIngredient.getIngredient().getName()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public String findUserNickname() {//{nickname}의 냉장고
        String email = JwtUtils.getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
        return user.getNickname();
    }

    @Transactional(readOnly = true)
    public UserIngredientDetailResponseDto findDetail(Long ingredientId) {
        // 사용자 조회
        String email = JwtUtils.getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
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
                userIngredient.getIsFrozen(),
                userIngredient.getIsRefrigerated()
        );
    }

    // 재료 정보 수정
    @Transactional
    public UserIngredientDetailResponseDto edit(Long ingredientId, UserIngredientRequestDto requestDto) {
        // 사용자 조회
        String email = JwtUtils.getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        // 사용자의 재료 조회
        UserIngredient userIngredient = userIngredientRepository.findByUserAndIngredientId(user, ingredientId)
                .orElseThrow(() -> new IllegalArgumentException("해당 재료를 찾을 수 없습니다."));

        // 수정할 필드 적용 (수정할 값이 존재하면 수정)
        if (requestDto.quantity() != null) {
            userIngredient.setQuantity(requestDto.quantity());
        }
        if(requestDto.purchaseDate()!=null){
            userIngredient.setPurchaseDate(requestDto.purchaseDate());
        }
        if (requestDto.expiredDate() != null) {
            userIngredient.setExpirationDate(requestDto.expiredDate());
        }
        if (requestDto.isFrozen() != null) {
            userIngredient.setIsFrozen(requestDto.isFrozen());
        }
        if (requestDto.isRefrigerated() != null) {
            userIngredient.setIsRefrigerated(requestDto.isRefrigerated());
        }

        // 저장
        userIngredientRepository.save(userIngredient);

        // 수정된 정보를 DTO로 변환하여 반환
        return new UserIngredientDetailResponseDto(
                userIngredient.getIngredient().getName(),
                userIngredient.getIngredient().getCategory(),
                userIngredient.getQuantity(),
                userIngredient.getPurchaseDate(),
                userIngredient.getExpirationDate(),
                userIngredient.getIsFrozen(),
                userIngredient.getIsRefrigerated()
        );
    }

    // 재료 삭제
    @Transactional
    public void delete(Long ingredientId) {
        // 사용자 조회
        String email = JwtUtils.getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        // 사용자의 재료 조회
        UserIngredient userIngredient = userIngredientRepository.findByUserAndIngredientId(user, ingredientId)
                .orElseThrow(() -> new IllegalArgumentException("해당 재료를 찾을 수 없습니다."));

        // 재료 삭제
        userIngredientRepository.delete(userIngredient);
    }
}
