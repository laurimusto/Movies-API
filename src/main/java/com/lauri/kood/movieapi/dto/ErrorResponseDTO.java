package com.lauri.kood.movieapi.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
        String message,
        int status,
        LocalDateTime timestamp) {
}
