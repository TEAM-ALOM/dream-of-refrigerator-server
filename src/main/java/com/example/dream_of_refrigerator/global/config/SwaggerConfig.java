package com.example.dream_of_refrigerator.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@OpenAPIDefinition(
        info=@Info(title = "냉장고의 꿈 api 문서",
                description = "기본적으로 유저 api 이외의 api들은 jwt를 해더에 붙여야 합니다. </br>" +
                        "이때 헤더에 << Authorization : Bearer [accessToken] >> 으로 보내시면 됩니다. </br>" +
                        "회원가입 이후 로그인 진행하시면 authToken 필드에 grantType, accessToken, refreshToken을 리턴합니다. </br>" +
                        "accessToken은 api 사용, refreshToken은 accessToken이 만료(30분 이후) 되었을때 새로운 accessToken과 refreshToken을 발급받기 위해 사용됩니다.",
                version ="v1"))
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openApi() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .security(Arrays.asList(securityRequirement));
    }
}
