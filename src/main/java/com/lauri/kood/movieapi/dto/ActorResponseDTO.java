package com.lauri.kood.movieapi.dto;

import java.time.LocalDate;

public record ActorResponseDTO(
        Long Id,
        String name,
        LocalDate birthdate) {
}
