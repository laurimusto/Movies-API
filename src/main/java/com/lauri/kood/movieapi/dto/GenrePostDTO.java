package com.lauri.kood.movieapi.dto;

import jakarta.validation.constraints.NotNull;

public record GenrePostDTO(@NotNull
                           String name) {
}
