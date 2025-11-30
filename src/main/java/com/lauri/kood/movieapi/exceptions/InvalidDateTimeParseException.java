package com.lauri.kood.movieapi.exceptions;

public class InvalidDateTimeParseException extends RuntimeException {
    public InvalidDateTimeParseException(String message) {
        super(message);
    }
}
