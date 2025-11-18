package com.lauri.kood.movieapi.dto;

import java.time.Year;
import java.util.Set;

public record MoviePatchDTO(String title,
                            Integer releaseYear,
                            Integer duration,
                            Set<Long> actors,
                            Set<Long> genres) {
}
