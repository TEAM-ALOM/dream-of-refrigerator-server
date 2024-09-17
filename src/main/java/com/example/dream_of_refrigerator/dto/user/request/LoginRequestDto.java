package com.example.dream_of_refrigerator.dto.user.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @Schema(format = "email")
    private String email;
    @Schema(format = "password")
    private String password;
}
