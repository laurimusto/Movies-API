package com.lauri.kood.movieapi.dto;
import java.time.LocalDate;
import java.util.Set;

public record ActorPatchDTO(
        String name,
        LocalDate birthdate,
        Set<Long> addMovieIds,
        Set<Long> removeMovieIds
) {
}
