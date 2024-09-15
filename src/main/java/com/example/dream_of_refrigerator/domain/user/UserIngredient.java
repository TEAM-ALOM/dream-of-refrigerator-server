package com.example.dream_of_refrigerator.domain.user;

import com.example.dream_of_refrigerator.domain.ingredient.Ingredient;
import com.example.dream_of_refrigerator.dto.ingredient.request.UserIngredientRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity(name = "user_ingredient")
@Getter
@Setter
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
    // 보관 방법 (냉장, 냉동)
    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isRefrigerated; // 냉장 보관 여부(기본값 false 0)

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isFrozen; // 냉동 보관 여부(기본값 false 0)

    private LocalDate purchaseDate; // 구매 날짜

    private LocalDate expirationDate; // 유통기한

    @Builder
    public UserIngredient(Ingredient ingredient, User user, Integer quantity, LocalDate purchaseDate, LocalDate expirationDate,Boolean isRefrigerated,Boolean isFrozen) {
        this.ingredient = ingredient;
        this.user = user;
        this.quantity = quantity;
        this.purchaseDate = (purchaseDate != null) ? purchaseDate : LocalDate.now(); // 기본값 설정
        this.expirationDate = expirationDate;
        this.isRefrigerated=isRefrigerated;
        this.isFrozen=isFrozen;
    }
    public UserIngredientRequestDto toDto() {
        return new UserIngredientRequestDto(
                user.getId(),
                ingredient.getId(),
                ingredient.getCategory(),
                quantity,
                purchaseDate,
                expirationDate,
                isRefrigerated,
                isFrozen);
    }
}