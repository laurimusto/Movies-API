package com.lauri.kood.movieapi.mapper;

import com.lauri.kood.movieapi.dto.GenreResponseDTO;
import com.lauri.kood.movieapi.entity.Genre;

public class GenreMapper {
    public static GenreResponseDTO toGenreResponseDto(Genre genre) {
        return new GenreResponseDTO(
                genre.getId(),
                genre.getName()
                //genre.getMovies()
        );
    }
}
