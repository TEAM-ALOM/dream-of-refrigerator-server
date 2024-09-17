package com.example.dream_of_refrigerator.service.user;


import com.example.dream_of_refrigerator.domain.user.User;
import com.example.dream_of_refrigerator.dto.user.request.LoginRequestDto;
import com.example.dream_of_refrigerator.dto.user.request.RefreshRequestDto;
import com.example.dream_of_refrigerator.dto.user.request.SignUpRequestDto;
import com.example.dream_of_refrigerator.dto.user.response.AuthToken;
import com.example.dream_of_refrigerator.dto.user.response.LoginResponseDto;
import com.example.dream_of_refrigerator.dto.user.response.SignUpResponseDto;
import com.example.dream_of_refrigerator.global.util.JwtUtils;
import com.example.dream_of_refrigerator.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        Collection<? extends GrantedAuthority> authorities = authenticate.getAuthorities();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        User user = (User) authenticate.getPrincipal();

        AuthToken authToken = JwtUtils.generateToken(email, roles);

        user.setRefreshToken(authToken.getRefreshToken());
        userRepository.save(user);

        return LoginResponseDto.builder()
                .authToken(authToken)
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }

    public SignUpResponseDto signUpUser(SignUpRequestDto signUpRequestDto) {
        User user = signUpRequestDto.toEntity();
        user.hashPassword(passwordEncoder);

        user.setRole();
        User save = userRepository.save(user);

        return SignUpResponseDto.builder()
                .nickname(save.getNickname())
                .email(save.getEmail())
                .build();

    }

    public String logout() {
        String email = JwtUtils.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));

        user.setRefreshToken(null);

        return "로그아웃 성공. User Id = " + user.getEmail();
    }
    @Transactional(readOnly = true)
    public Boolean checkNickname(String nickname){
        Optional<User> userOptional = userRepository.findByNickname(nickname);
        return userOptional.isEmpty();

    }
    @Transactional(readOnly = true)
    public Boolean checkEmail(String email){
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.isEmpty();
    }

    public AuthToken refresh(RefreshRequestDto refreshRequestDto){
        String refreshToken = refreshRequestDto.getRefreshToken();

        JwtUtils.validateToken(refreshToken);

        String email = JwtUtils.getEmailFromRefreshToken(refreshToken);

        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니디."));

        String role = findUser.getRole();
        List<String> roles = new ArrayList<>();

        for (String s : role.split(",")) {
            roles.add(s);
        }

        AuthToken newAuthToken = JwtUtils.generateToken(findUser.getEmail(), roles);
        findUser.setRefreshToken(newAuthToken.getRefreshToken());
        userRepository.save(findUser);

        return newAuthToken;
    }
}
