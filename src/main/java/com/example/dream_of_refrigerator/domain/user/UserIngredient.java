package com.example.dream_of_refrigerator.domain.user;

import com.example.dream_of_refrigerator.domain.ingredient.Ingredient;
import com.example.dream_of_refrigerator.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "user_ingredient")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient; // 어떤 재료인지 참조

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 어떤 사용자의 재료인지 참조

    @Column(name = "quantity", columnDefinition = "int")
    private Integer quantity; // 보유한 수량

    private LocalDate purchaseDate; // 구매 날짜

    private LocalDate expirationDate; // 유통기한
}