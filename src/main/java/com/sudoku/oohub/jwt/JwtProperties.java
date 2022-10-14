package com.sudoku.oohub.jwt;

public interface JwtProperties {
    int EXPIRATION_TIME = 60000*60; // 30분
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
