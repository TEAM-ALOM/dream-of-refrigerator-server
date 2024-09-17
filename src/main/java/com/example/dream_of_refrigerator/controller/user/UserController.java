package com.example.dream_of_refrigerator.controller.user;

import com.example.dream_of_refrigerator.dto.user.request.LoginRequestDto;
import com.example.dream_of_refrigerator.dto.user.request.RefreshRequestDto;
import com.example.dream_of_refrigerator.dto.user.request.SignUpRequestDto;
import com.example.dream_of_refrigerator.dto.user.response.AuthToken;
import com.example.dream_of_refrigerator.dto.user.response.LoginResponseDto;
import com.example.dream_of_refrigerator.dto.user.response.SignUpResponseDto;
import com.example.dream_of_refrigerator.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "유저 API", description = "회원가입 및 로그인을 위한 API입니다.")
public class UserController {
    private final UserService userService;
    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 사용할 닉네임을 입력하여 회원가입을 진행합니다.")
    @ApiResponse(responseCode = "200", description = "회원가입 성공")
    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        return ResponseEntity.ok(userService.signUpUser(signUpRequestDto));
    }
    @Operation(summary = "로그인", description = "이메일, 비밀번호를 입력하여 로그인을 진행합니다.")
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(userService.login(loginRequestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(){
        return ResponseEntity.ok(userService.logout());
    }

    @PostMapping("/check/nickname")
    public ResponseEntity<Boolean> checkNickname(@RequestParam(value = "nickname") String nickname){
        return ResponseEntity.ok(userService.checkNickname(nickname));
    }

    @PostMapping("/check/email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam(value = "email") String email){
        return ResponseEntity.ok(userService.checkEmail(email));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthToken> refresh(@RequestBody RefreshRequestDto refreshRequestDto){
        return ResponseEntity.ok(userService.refresh(refreshRequestDto));
    }
}
