package com.lauri.kood.movieapi.exceptions;

import com.lauri.kood.movieapi.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Time;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                ex.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> handleAll(Exception ex) {
        return new ResponseEntity<>("An unexpected error occured.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
