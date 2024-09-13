package com.example.dream_of_refrigerator.domain.user;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    // 조회용 양방향 매핑
    @OneToMany(mappedBy = "user")
    private List<Favorite> favorites;
}
