package com.example.dream_of_refrigerator.dto.user.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SignUpResponseDto {
    private String email;
    private String nickname;
}
