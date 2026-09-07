package com.example.libraryAPI.loan.api;

import com.example.libraryAPI.loan.service.BookItemAlreadyBorrowedException;
import com.example.libraryAPI.loan.service.LoanAlreadyReturnedException;
import com.example.libraryAPI.loan.service.LoanNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LoanExceptionHandler {

    @ExceptionHandler(LoanNotFoundException.class)
    public ResponseEntity<String> handleLoanNotFound(LoanNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler({
            BookItemAlreadyBorrowedException.class,
            LoanAlreadyReturnedException.class
    })
    public ResponseEntity<String> handleBadRequest(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}