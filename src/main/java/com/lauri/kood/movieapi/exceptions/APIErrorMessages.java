package com.lauri.kood.movieapi.exceptions;

import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;

public enum APIErrorMessages {
    //USER
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "User already exists"),
    //CUSTOMER
    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, "Customer not found"),
    CUSTOMER_ALREADY_EXISTS(HttpStatus.CONFLICT, "Customer already exists"),
    //FILES
    CREATE_DIRECTORY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create a directory"),
    UPLOAD_IMAGE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload image"),
    DELETE_IMAGE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete image"),
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "Image not found"),

    //GENERIC
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Something wrong happened"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad request"),
    READ_IMAGE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to read image");

    private final HttpStatus httpStatus;
    private final String description;

    private APIErrorMessages(HttpStatus httpStatus, String description) {
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
