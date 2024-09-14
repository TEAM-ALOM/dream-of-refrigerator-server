package com.example.dream_of_refrigerator.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
@Builder
public class LoginResponseDto {
    private String email;
    private String nickname;
    private AuthToken authToken;
}
