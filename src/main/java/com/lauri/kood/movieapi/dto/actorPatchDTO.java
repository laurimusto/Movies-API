package com.lauri.kood.movieapi.dto;
import java.time.LocalDate;

public record actorPatchDTO(
        Long id,
        String name,
        LocalDate birthDate) {
}
