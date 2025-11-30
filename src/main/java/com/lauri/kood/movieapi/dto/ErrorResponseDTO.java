package com.lauri.kood.movieapi.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
        int status,
        String error,
        String message) {
}
