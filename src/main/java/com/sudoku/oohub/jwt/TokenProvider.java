package com.sudoku.oohub.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
@AllArgsConstructor
public class TokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private CustomUserDetails userDetails;

    public String createToken(Authentication authentication){
        userDetails = (CustomUserDetails) authentication.getPrincipal();

        return JWT.create()
                .withSubject(userDetails.getUsername()) // 토큰 이름
                .withExpiresAt(new Date(System.currentTimeMillis() + (JwtProperties.EXPIRATION_TIME)))
                .withClaim("id", userDetails.getMember().getId())
                .withClaim("username", userDetails.getMember().getUsername())
                .sign(Algorithm.HMAC512(secretKey));
    }
}
