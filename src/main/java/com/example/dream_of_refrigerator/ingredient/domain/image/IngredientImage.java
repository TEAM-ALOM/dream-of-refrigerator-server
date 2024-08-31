package com.example.dream_of_refrigerator.ingredient.domain.image;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.antlr.v4.runtime.misc.NotNull;
@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class IngredientImage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;
    @Column
    @NotNull
    private String originalFilename;
    @Column
    @NotNull
    private String newFilename;
    @Column
    @NotNull
    private String filePath;
    @Column
    @NotNull
    private Long size;
}
