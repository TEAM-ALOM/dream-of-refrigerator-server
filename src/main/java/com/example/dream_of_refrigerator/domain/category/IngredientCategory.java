package com.example.dream_of_refrigerator.domain.category;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class IngredientCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //1,2,3,자동생성전략
    private Long id;

    @Column
    private String name;

    //@OneToMany(mappedBy = "ingredientCategory") //하나의 카테고리에 여러 재료가 속함, Ingredient 엔티티 내의 필드 이름 ingredientCategory참조
    //private List<Ingredient> ingredients;  // 해당 카테고리에 속한 재료 목록
}
