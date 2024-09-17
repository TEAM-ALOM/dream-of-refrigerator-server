package com.example.dream_of_refrigerator.domain.recipe;

import com.example.dream_of_refrigerator.domain.ingredient.Ingredient;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "ingredient_recipe")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String quantity;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;


}
