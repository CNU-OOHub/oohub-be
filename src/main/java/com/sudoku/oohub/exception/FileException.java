package com.sudoku.oohub.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class FileException extends RuntimeException{
    private final HttpStatus status = HttpStatus.BAD_REQUEST;
    private final String errorMessage;
}
