package org.madalina.productsinventory.controller;

import org.madalina.productsinventory.exceptions.InvalidExpDateException;
import org.madalina.productsinventory.exceptions.InvalidPriceException;
import org.madalina.productsinventory.exceptions.InvalidQuantityException;
import org.madalina.productsinventory.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


//sau: @ExceptionHandler({InvalidQuantit.class, InvalidPrice.class})
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidQuantityException.class)
    public ResponseEntity<?> handleInvalidQuantityException(InvalidQuantityException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPriceException.class)
    public ResponseEntity<String> handleInvalidPriceException(InvalidPriceException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidExpDateException.class)
    public ResponseEntity<String> handleInvalidExpirationDateException(InvalidExpDateException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
        // Returnează un răspuns cu statusul 404 Not Found
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
//        // raspuns general pt orice alta eroare
//        return new ResponseEntity<>("A aparut o eroare interna. Va rugam sa incercati mai tarziu.", HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}
