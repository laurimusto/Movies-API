package com.lauri.kood.movieapi.dto;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.Year;
import java.util.Set;

public record MoviePatchDTO(String title,
                            @Past
                            Integer releaseYear,
                            @Positive
                            Integer duration,
                            Set<Long> addActorIds,
                            Set<Long> addGenreIds,
                            Set<Long> removeActorIds,
                            Set<Long>removeGenreIds) {
}
