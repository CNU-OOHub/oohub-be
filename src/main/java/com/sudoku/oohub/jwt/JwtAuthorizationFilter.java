package com.sudoku.oohub.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sudoku.oohub.domain.Member;
import com.sudoku.oohub.exception.NameNotFoundException;
import com.sudoku.oohub.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private final MemberRepository memberRepository;

    @Value("${jwt.secret}")
    private String secretKey;


    // 인증이나 권한이 필요한 요청의 경우 해당 필터를 탄다.
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String accessToken = httpServletRequest.getHeader(JwtProperties.HEADER_STRING);
        logger.debug("jwt header: " + accessToken);

        if (accessToken == null || !accessToken.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }
        // jwt 토큰을 검증해서 정상적인 사용자인지 확인
        String jwtToken = httpServletRequest.getHeader("Authorization").replace(JwtProperties.TOKEN_PREFIX, "");

        String username =
                JWT.require(Algorithm.HMAC512(secretKey)).build().verify(jwtToken).getClaim("username").asString();

        // 서명이 정상적으로 됨 -> 인증이 되었으므로 authentication 객체 만듦
        if (username != null) {
            Member memberEntity
                    = memberRepository.findByUsername(username).orElseThrow(() -> new NameNotFoundException("사용자 정보를 찾을 수 없습니다."));

            CustomUserDetails principalDetails = new CustomUserDetails(memberEntity);
            // jwt 토큰 서명이 정상이면 Authentication 객체 만듦
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    principalDetails, "", principalDetails.getAuthorities());

            // 강제로 시큐리티 세션에 접근하여 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        chain.doFilter(request, response);
    }
}
