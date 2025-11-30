package com.lauri.kood.movieapi;

public class PaginationValidator {

    /**
     * Validates pagination parameters before use.
     *
     * @param pageNumber the zero-based page number (must be non-negative)
     * @param pageSize the number of items per page (must be positive and not exceed maximum)
     * @throws IllegalArgumentException if page number is negative, page size is not positive,
     *         or page size exceeds the maximum allowed value
     */
    public static void validate(int pageNumber, int pageSize) {
        if (pageNumber < 0) {
            throw new IllegalArgumentException("Page number must not be negative");
        }

        if (pageSize <= 0) {
            throw new IllegalArgumentException("Page size must be greater than zero");
        }

        if (pageSize > 100) {
            throw new IllegalArgumentException("Page size must not exceed " + 100);
        }

    }

}