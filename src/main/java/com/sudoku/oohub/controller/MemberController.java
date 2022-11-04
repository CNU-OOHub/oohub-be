package com.sudoku.oohub.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sudoku.oohub.domain.Role;
import com.sudoku.oohub.dto.request.CreateMemberDto;
import com.sudoku.oohub.dto.request.LoginDto;
import com.sudoku.oohub.dto.response.MemberDto;
import com.sudoku.oohub.dto.response.TokenDto;
import com.sudoku.oohub.jwt.JwtProperties;
import com.sudoku.oohub.jwt.TokenProvider;
import com.sudoku.oohub.service.MemberService;
import com.sudoku.oohub.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @PostMapping("/v1/join")
    public ResponseEntity<Long> join(@RequestBody @Validated CreateMemberDto createMemberDto) {
        Long memberId = memberService.join(createMemberDto);
        return ResponseEntity.ok(memberId);
    }

    @PostMapping("/v1/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Validated LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        // PrincipalDetailsService의 loadUserByUsername 실행됨 -> 정상이면 authentication 객체 리턴
        // 즉 알아서 인증을 해줌 (= DB에 있는 username, password와 일치한다.)
        // authentication에는 내 로그인 정보가 담긴다.
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.createToken(authentication);
        String refreshToken = tokenProvider.refreshToken(authentication);
        MemberDto memberDto = memberService.findByUsername(loginDto.getUsername());

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + accessToken);
        TokenDto tokenDto = TokenDto.from(
                accessToken, refreshToken, memberDto.getUsername(), memberDto.getDepartmentDto().getName(), memberDto.getRole() == Role.ROLE_ADMIN);
        return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/v1/refresh")
    public ResponseEntity<TokenDto> refreshToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(JwtProperties.HEADER_STRING_REFRESH);
        if(authorizationHeader != null && authorizationHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            String refreshToken = authorizationHeader.substring(JwtProperties.TOKEN_PREFIX.length());
            // refresh token 인증
            String username = tokenProvider.getUsernameIfValidRefreshToken(refreshToken).orElseThrow(
                    () -> new RuntimeException("refresh token 검증 실패"));

            // access token 재발급
            MemberDto memberDto = memberService.findByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(memberDto.getUsername(), memberDto.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            String newAccessToken = tokenProvider.createToken(authentication);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + newAccessToken);
            TokenDto tokenDto = TokenDto.from(
                    newAccessToken, refreshToken, memberDto.getUsername(), memberDto.getDepartmentDto().getName(), memberDto.getRole() == Role.ROLE_ADMIN);

            return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
        }else {
            throw new RuntimeException("Refresh token 을 넣어주세요.");
        }
    }

    @GetMapping("/v1/users")
    public ResponseEntity<String> test() {
        String currentUsername = SecurityUtil.getCurrentUsername().orElseGet(null);
        return ResponseEntity.ok(currentUsername);
    }

}
