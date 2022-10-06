package com.sudoku.oohub.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class IdNotFoundException extends RuntimeException{
    private final HttpStatus status = HttpStatus.NOT_FOUND;
    private final String errorMessage;
}
