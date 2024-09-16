package com.example.dream_of_refrigerator.controller.user;

import com.example.dream_of_refrigerator.dto.user.request.LoginRequestDto;
import com.example.dream_of_refrigerator.dto.user.request.RefreshRequestDto;
import com.example.dream_of_refrigerator.dto.user.request.SignUpRequestDto;
import com.example.dream_of_refrigerator.dto.user.response.AuthToken;
import com.example.dream_of_refrigerator.dto.user.response.LoginResponseDto;
import com.example.dream_of_refrigerator.dto.user.response.SignUpResponseDto;
import com.example.dream_of_refrigerator.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        return ResponseEntity.ok(userService.signUpUser(signUpRequestDto));
    }

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
