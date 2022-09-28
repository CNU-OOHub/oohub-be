package com.sudoku.oohub.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DuplicateMemberException extends RuntimeException{
    private final String errorMessage;
}
