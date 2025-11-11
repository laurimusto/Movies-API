package com.lauri.kood.movieapi.dto;


import com.lauri.kood.movieapi.entity.Actor;
import com.lauri.kood.movieapi.entity.Genre;
import jakarta.persistence.Column;

import java.util.List;
import java.util.Set;

/*
We are using List because we might want ordering of the list (lead actors maybe)
and Set because we want to ensure that we have no duplicates and we don't need ordering of genres.

Genres → Set (order meaningless, uniqueness matters).
Actors → List if you want order or predictable display;
Set if you only care about unique membership and not sequence.
 */
public record MovieResponseDTO(
        Long Id,
        String title,
        String releaseYear,
        String duration,
        List<ActorResponseDTO> actors,
        Set<GenreResponseDTO> genres) {
}
