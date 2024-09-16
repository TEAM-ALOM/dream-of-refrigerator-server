package com.example.dream_of_refrigerator.repository.ingredient;

import com.example.dream_of_refrigerator.domain.ingredient.Ingredient;
import com.example.dream_of_refrigerator.domain.user.User;
import com.example.dream_of_refrigerator.domain.user.UserIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserIngredientRepository extends JpaRepository<UserIngredient,Long> {
    List<UserIngredient> findByUser(User user);
    Optional<UserIngredient> findByUserAndIngredientId(User user, Long ingredientId);

    Optional<UserIngredient> findById(Long ingredientId);
    // 특정 유저가 특정 재료를 이미 보유하고 있는지 확인
    Optional<UserIngredient> findByUserAndIngredient(User user, Ingredient ingredient);
}
