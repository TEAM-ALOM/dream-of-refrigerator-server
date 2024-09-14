package com.example.dream_of_refrigerator.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@AllArgsConstructor
public class AuthToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}