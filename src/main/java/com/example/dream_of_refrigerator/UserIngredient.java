package com.example.dream_of_refrigerator;

import com.example.dream_of_refrigerator.ingredient.domain.ingredient.Ingredient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient; // 어떤 재료인지 참조

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 어떤 사용자의 재료인지 참조

    private int quantity; // 보유한 수량
    private LocalDate purchaseDate; // 구매 날짜
    private LocalDate expirationDate; // 유통기한
}