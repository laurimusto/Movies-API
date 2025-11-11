package com.lauri.kood.movieapi.dto;

public record MovieRequestDTO(
        String title,
        String releaseYear,
        String duration
) {
}
