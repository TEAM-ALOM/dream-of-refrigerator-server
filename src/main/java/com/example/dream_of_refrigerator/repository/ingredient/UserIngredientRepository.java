package com.example.dream_of_refrigerator.repository.ingredient;

import com.example.dream_of_refrigerator.domain.user.User;
import com.example.dream_of_refrigerator.domain.user.UserIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserIngredientRepository extends JpaRepository<UserIngredient,Long> {
    List<UserIngredient> findByUser(User user);
    Optional<UserIngredient> findByUserAndIngredientId(User user, Long ingredientId);

    Optional<UserIngredient> findById(Long ingredientId);
}
