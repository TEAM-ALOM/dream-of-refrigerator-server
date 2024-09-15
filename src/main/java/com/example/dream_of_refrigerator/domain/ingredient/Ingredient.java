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



    @OneToMany(mappedBy = "ingredient")
    @Builder.Default
    private Set<UserIngredient> userIngredients = new LinkedHashSet<>();

    /*
    long과 Long의 차이
    long : 원시타입 (null할당 불가능)
    Long : 참조타입 (null할당 가능)
    */

}
//@Builder.Default
//private Set<IngredientRecipe> ingredientRecipes = new LinkedHashSet<>();
