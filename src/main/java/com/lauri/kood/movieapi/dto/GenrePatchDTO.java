package com.lauri.kood.movieapi.dto;

import jakarta.validation.constraints.NotBlank;

public record GenrePatchDTO(@NotBlank(message = "Name must not be blank")
                            String name) {
}
