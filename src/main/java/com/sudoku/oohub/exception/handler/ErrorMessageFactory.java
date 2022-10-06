package com.sudoku.oohub.exception.handler;

import com.sudoku.oohub.exception.handler.ErrorMessage;
import org.springframework.http.HttpStatus;

public class ErrorMessageFactory {

    public static ErrorMessage from(HttpStatus httpStatus, String message) {
        return ErrorMessage.builder()
                .status(httpStatus.value())
                .error(httpStatus.name())
                .message(message)
                .build();
    }
}
