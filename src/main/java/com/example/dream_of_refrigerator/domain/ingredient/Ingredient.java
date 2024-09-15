package com.example.dream_of_refrigerator.domain.ingredient;

import com.example.dream_of_refrigerator.domain.recipe.IngredientRecipe;
import com.example.dream_of_refrigerator.domain.user.UserIngredient;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Entity(name = "ingredient")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String name;    //재료 이름

    // 보관 방법 (냉장, 냉동)
    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isRefrigerated; // 냉장 보관 여부(기본값 false 0)

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isFrozen; // 냉동 보관 여부(기본값 false 0)

    @OneToMany(mappedBy = "ingredient")
    @Builder.Default
    private Set<IngredientRecipe> ingredientRecipes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "ingredient")
    @Builder.Default
    private Set<UserIngredient> userIngredients = new LinkedHashSet<>();
    /*
    long과 Long의 차이
    long : 원시타입 (null할당 불가능)
    Long : 참조타입 (null할당 가능)
    */

}
