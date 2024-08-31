package com.example.dream_of_refrigerator.ingredient.domain.ingredient;
import com.example.dream_of_refrigerator.ingredient.domain.category.IngredientCategory;
import com.example.dream_of_refrigerator.ingredient.domain.image.IngredientImage;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private String uuid;
    @PrePersist //DB에 저장될 때 uuid가 먼저 생성된 후 저장되게끔 하기
    public void prePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
    }

    @Column(nullable = false)
    private String name;    //재료 이름

    @ManyToOne  //해당 재료 엔티티 여러 개가 하나의 재료카테고리에 연관된다.
    private IngredientCategory ingredientCategory;  //재료가 속한 카테고리(육류, 채소, ..)
    @Column(nullable = false)
    private boolean ownedByUser = false; // 보유한 재료인지 여부, 기본값을 false로 설정
    // 보관 방법 (냉장, 냉동)
    @Column(nullable = false)
    private boolean isRefrigerated = false; // 냉장 보관 여부(기본값 false 0)

    @Column(nullable = false)
    private boolean isFrozen = false; // 냉동 보관 여부(기본값 false 0)

//    @Column(nullable = true)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
//    private LocalDate purchaseDate; //재료 구입 날짜(냉장고 입주 날짜)
//
//    @Column(nullable = true)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
//    private LocalDate expirationDate;   //소비기한(사용자가 설정)
//
//    // 소비 기한까지 남은 일 수 계산
//    public int getRemainingDays(){ //int아님 long
//        return LocalDate.now().until(expirationDate).getDays();
//    }

    public void setOwnedByUser(boolean b) {
        this.ownedByUser=b;
    }
    public void setRefrigerated(boolean refrigerated) {
        this.isRefrigerated = refrigerated;
    }

    public void setFrozen(boolean frozen) {
        this.isFrozen = frozen;
    }

    /*
    long과 Long의 차이
    long : 원시타입 (null할당 불가능)
    Long : 참조타입 (null할당 가능)
    */
    @OneToOne
    private IngredientImage ingredientImage;  //재료 아이콘

}
