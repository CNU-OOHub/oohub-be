package com.sudoku.oohub.jwt;

public interface JwtProperties {
    int EXPIRATION_TIME = 10 * 60 * 1000; // 60분
    int REFRESH_EXPIRATION_TIME = 300 * 60 * 1000;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
    String HEADER_STRING_REFRESH = "Refresh";
}
