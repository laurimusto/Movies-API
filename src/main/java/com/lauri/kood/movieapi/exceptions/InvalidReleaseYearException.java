package com.lauri.kood.movieapi.exceptions;

public class InvalidReleaseYearException extends RuntimeException {
    public InvalidReleaseYearException(String message) {
        super(message);
    }
}
