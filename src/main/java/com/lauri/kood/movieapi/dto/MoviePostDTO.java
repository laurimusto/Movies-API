package com.lauri.kood.movieapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.Year;
import java.util.List;
import java.util.Set;

public record MoviePostDTO(
        @NotNull(message = "Title is required")
        String title,

        @NotNull(message = "Release year is required")
        @Min(value = 1888, message = "Release year must be no earlier than 1888")
        Integer releaseYear,

        @NotNull(message = "Duration is required")
        @Positive(message = "Duration must be a positive number")
        Integer duration,

        @NotNull
        Set<Long> actors,

        @NotNull
        Set<Long> genres) {
}
