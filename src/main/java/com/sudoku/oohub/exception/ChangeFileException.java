package com.sudoku.oohub.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ChangeFileException extends RuntimeException{
    private final HttpStatus status = HttpStatus.CONFLICT;
    private final String errorMessage;
}
