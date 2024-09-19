package com.example.dream_of_refrigerator.global.util;



import com.example.dream_of_refrigerator.dto.user.response.AuthToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {

    private static String JWT_SECRET_KEY;

    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 30L;

    private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 30L;
    @Value("${spring.jwt.secret}")
    public void setKey(String key){
        JWT_SECRET_KEY = key;
    }

    public static String generateAccessToken(String userId, List<String> roles){
        return Jwts.builder()
                .setSubject(userId)
                .claim("roles", roles)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME + 1000 * 60 * 60 * 24 * 365L))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY)
                .compact();

    }
    public static String generateRefreshToken(String userId){
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY)
                .compact();
    }

    public static AuthToken generateToken(String userId, List<String> roles) {
        return AuthToken.builder()
                .grantType("Bearer")
                .accessToken(JwtUtils.generateAccessToken(userId, roles))
                .refreshToken(JwtUtils.generateRefreshToken(userId))
                .build();
    }



    public static boolean validateToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(JWT_SECRET_KEY).build().parseClaimsJws(token);
            return true;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public static Claims parseClaims(String token){
        try{
            return Jwts.parserBuilder().setSigningKey(JWT_SECRET_KEY).build().parseClaimsJws(token).getBody();
        }
        catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }

    public static String getEmail(){
        String accessToken = JwtUtils.getAccessToken();
        Claims body = JwtUtils.parseClaims(accessToken);
        String email = body.get("sub", String.class);

        return email;
    }
    public static String getAccessToken(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }
        else
        {
            throw new RuntimeException("Invalid token");
        }

        return token;
    }

    public static String getEmailFromRefreshToken(String refreshToken){
        Claims body = JwtUtils.parseClaims(refreshToken);
        String email = body.get("sub", String.class);

        return email;
    }
}
