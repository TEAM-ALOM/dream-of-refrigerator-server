package com.example.dream_of_refrigerator.dto.user.request;

import com.example.dream_of_refrigerator.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequestDto {
    @Schema(format = "email")
    private String email;
    @Schema(format = "password")
    private String password;
    @Schema(format = "nickname")
    private String nickname;

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
