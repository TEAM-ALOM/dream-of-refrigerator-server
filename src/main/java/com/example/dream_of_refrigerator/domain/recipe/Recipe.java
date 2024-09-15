package com.example.dream_of_refrigerator.domain.recipe;

import com.example.dream_of_refrigerator.domain.user.Favorite;
import jakarta.persistence.*;
import lombok.*;


import java.util.LinkedHashSet;
import java.util.Set;

@Entity(name = "recipe")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String category;

    @Column(name = "thumbnail", columnDefinition = "TEXT")
    private String thumbnail;

    @OneToMany(mappedBy = "recipe")
    @Builder.Default
    private Set<RecipeDetail> recipeDetails = new LinkedHashSet<>();

    @OneToMany(mappedBy = "recipe")
    @Builder.Default
    private Set<IngredientRecipe> ingredientRecipes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "recipe")
    private Set<Favorite> favorites;
}
