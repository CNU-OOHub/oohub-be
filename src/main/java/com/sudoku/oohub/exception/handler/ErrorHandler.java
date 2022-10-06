package com.sudoku.oohub.exception.handler;

import com.sudoku.oohub.exception.DuplicateDepartmentException;
import com.sudoku.oohub.exception.DuplicateMemberException;
import com.sudoku.oohub.exception.UsernameNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorMessage> invalidIdExceptionHandler(UsernameNotFoundException e){
        return ResponseEntity.status(e.getStatus())
                .body(ErrorMessageFactory.from(e.getStatus(), e.getErrorMessage()));
    }

    @ExceptionHandler(DuplicateMemberException.class)
    public ResponseEntity<ErrorMessage> invalidEmailExceptionHandler(DuplicateMemberException e){
        return ResponseEntity.status(e.getStatus())
                .body(ErrorMessageFactory.from(e.getStatus(), e.getErrorMessage()));
    }

    @ExceptionHandler(DuplicateDepartmentException.class)
    public ResponseEntity<ErrorMessage> invalidEmailExceptionHandler(DuplicateDepartmentException e){
        return ResponseEntity.status(e.getStatus())
                .body(ErrorMessageFactory.from(e.getStatus(), e.getErrorMessage()));
    }
}
