package com.lauri.kood.movieapi.dto;
import java.time.LocalDate;

public record ActorPatchDTO(
        Long id,
        String name,
        LocalDate birthdate
) {
}
