package com.sudoku.oohub.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class DuplicateOrganizationException extends RuntimeException{
    private final HttpStatus status = HttpStatus.CONFLICT;
    private final String errorMessage;
}
