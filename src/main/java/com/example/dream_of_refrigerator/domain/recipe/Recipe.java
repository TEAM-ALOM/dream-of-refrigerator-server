package com.example.dream_of_refrigerator.domain.recipe;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.util.Set;

@Entity(name = "recipe")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String category;

    @Column(name = "thumbnail", columnDefinition = "TEXT")
    private String thumbnail;


    @OneToMany(mappedBy = "recipe")
    private Set<RecipeDetail> recipeDetails;

    @OneToMany(mappedBy = "recipe")
    private Set<IngredientRecipe> ingredientRecipes;
}
