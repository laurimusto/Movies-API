package com.lauri.kood.movieapi.dto;

import java.util.List;
import java.util.Set;

public record MoviePostDTO(
        String title,
        String releaseYear,
        String duration,
        Set<Long> actors,
        Set<Long> genres) {

}
