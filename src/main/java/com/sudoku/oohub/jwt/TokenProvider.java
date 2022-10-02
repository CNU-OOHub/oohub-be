package com.sudoku.oohub.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sudoku.oohub.service.CustomUserDetailsService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
@AllArgsConstructor
public class TokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private CustomUserDetails userDetails;
    private CustomUserDetailsService userDetailsService;

    public String createToken(Authentication authentication){
        userDetails = (CustomUserDetails) authentication.getPrincipal();

        return JWT.create()
                .withSubject(userDetails.getUsername()) // 토큰 이름
                .withExpiresAt(new Date(System.currentTimeMillis() + (JwtProperties.EXPIRATION_TIME))) // 토큰 유효 시간 (10분)
                .withClaim("id", userDetails.getMember().getId())
                .withClaim("username", userDetails.getMember().getUsername())
                .sign(Algorithm.HMAC512(secretKey));
    }


}
