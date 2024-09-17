package com.example.dream_of_refrigerator.domain.recipe;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "recipe_detail")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Integer sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="recipe_id")
    private Recipe recipe;
}
