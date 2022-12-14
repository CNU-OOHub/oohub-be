package com.sudoku.oohub.exception.handler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.sudoku.oohub.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(DuplicateMemberException.class)
    public ResponseEntity<ErrorMessage> invalidEmailExceptionHandler(DuplicateMemberException e){
        return ResponseEntity.status(e.getStatus())
                .body(ErrorMessageFactory.from(e.getStatus(), e.getErrorMessage()));
    }

    @ExceptionHandler(DuplicateMemberOrganizationException.class)
    public ResponseEntity<ErrorMessage> duplicateMemberOrganizationExceptionHandler(DuplicateMemberOrganizationException e){
        return ResponseEntity.status(e.getStatus())
                .body(ErrorMessageFactory.from(e.getStatus(), e.getErrorMessage()));
    }

    @ExceptionHandler(DuplicateOrganizationException.class)
    public ResponseEntity<ErrorMessage> duplicateOrganizationExceptionHandler(DuplicateOrganizationException e){
        return ResponseEntity.status(e.getStatus())
                .body(ErrorMessageFactory.from(e.getStatus(), e.getErrorMessage()));
    }

    @ExceptionHandler(DuplicateDepartmentException.class)
    public ResponseEntity<ErrorMessage> duplicateDepartmentExceptionHandler(DuplicateDepartmentException e){
        return ResponseEntity.status(e.getStatus())
                .body(ErrorMessageFactory.from(e.getStatus(), e.getErrorMessage()));
    }

    @ExceptionHandler(DuplicateFileException.class)
    public ResponseEntity<ErrorMessage> duplicateFileExceptionHandler(DuplicateFileException e){
        return ResponseEntity.status(e.getStatus())
                .body(ErrorMessageFactory.from(e.getStatus(), e.getErrorMessage()));
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ErrorMessage> idNotFoundExceptionHandler(IdNotFoundException e) {
        return ResponseEntity.status(e.getStatus())
                .body(ErrorMessageFactory.from(e.getStatus(), e.getErrorMessage()));
    }

    @ExceptionHandler(NameNotFoundException.class)
    public ResponseEntity<ErrorMessage> nameNotFoundExceptionHandler(NameNotFoundException e) {
        return ResponseEntity.status(e.getStatus())
                .body(ErrorMessageFactory.from(e.getStatus(), e.getErrorMessage()));
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<ErrorMessage> fileExceptionHandler(FileException e) {
        return ResponseEntity.status(e.getStatus())
                .body(ErrorMessageFactory.from(e.getStatus(), e.getErrorMessage()));
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorMessage> invalidEmailExceptionHandler(FileNotFoundException e) {
        return ResponseEntity.status(e.getStatus())
                .body(ErrorMessageFactory.from(e.getStatus(), e.getErrorMessage()));
    }

    @ExceptionHandler(ChangeFileException.class)
    public ResponseEntity<ErrorMessage> changeFileExceptionHandler(ChangeFileException e) {
        return ResponseEntity.status(e.getStatus())
                .body(ErrorMessageFactory.from(e.getStatus(), e.getErrorMessage()));
    }


    @ExceptionHandler(com.auth0.jwt.exceptions.TokenExpiredException.class)
    public ResponseEntity<ErrorMessage> tokenExpiredExceptionHandler(TokenExpiredException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorMessageFactory.from(HttpStatus.UNAUTHORIZED, e.getMessage()));
    }
}
