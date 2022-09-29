package com.sudoku.oohub.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UsernameNotFoundException extends RuntimeException{
    private final String errorMessage;
}
