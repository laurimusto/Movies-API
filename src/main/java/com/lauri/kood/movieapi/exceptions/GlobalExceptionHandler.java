package com.lauri.kood.movieapi.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.lauri.kood.movieapi.dto.ErrorResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    private ResponseEntity<ErrorResponseDTO> createErrorResponse( //helper to create response for exception.
                                                                  HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponseDTO(status.value(), status.getReasonPhrase(), message));
    }

    //for handling "404 error"(Not Found)
    @ExceptionHandler({ResourceNotFoundException.class, ResourceInUseException.class})
//which Exceptions we are catching.
    public ResponseEntity<ErrorResponseDTO> handleNotFound(RuntimeException ex) {
        return createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(IllegalArgumentException e) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    // for handling "400 error"(Bad Request)
    @ExceptionHandler({
            InvalidDateException.class,
            InvalidDateFormatException.class,
            InvalidDurationException.class,
            InvalidReleaseYearException.class,

    })
    public ResponseEntity<ErrorResponseDTO> handleBadRequest(Exception ex) {
        return createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        // get the deepest cause and a default message
        Throwable cause = ex.getMostSpecificCause();
        String message = ex.getMessage();

        if (cause != null) {
            if (cause instanceof DateTimeParseException) {
                // date string couldn't be parsed
                message = "Invalid date: " + cause.getMessage();
            } else if (cause instanceof InvalidFormatException) {
                // value couldn't be converted to expected type
                message = "Invalid value: " + cause.getMessage();
            } else {
                // other parse error
                message = cause.getMessage();
            }
        }

        // return 400 with a short, clear parse error message
        return createErrorResponse(HttpStatus.BAD_REQUEST, "JSON parse error: " + message);
    }



    // Handles Spring's MethodArgumentNotValidException thrown when @Valid validation fails.
// Returns a clear, human-readable 400 response with per-field error messages.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException e) {
        // Map to collect field, errorMessage pairs (e.g. "name" -> "must not be blank")
        Map<String, String> errors = new HashMap<>();

        // Extract each field error and store a human-friendly message
        // err.getField() is the name of the invalid field
        // err.getDefaultMessage() is the validation message (from annotation or custom)
        e.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        // Build a consistent JSON body:
        // {
        //   "status": 400,
        //   "error": "Validation Failed",
        //   "errors": { "field1": "message1", "field2": "message2", ... }
        // }
        // Return 400 Bad Request so clients know the request data needs fixing.
        return ResponseEntity.badRequest().body(Map.of(
                "status", 400,
                "error", "Validation Failed",
                "errors", errors
        ));
    }

    @ExceptionHandler(Exception.class) //handles everything else.
    public ResponseEntity<ErrorResponseDTO> handleAll(Exception ex) {
        log.error("Unhandled exception", ex);
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
    }

}
