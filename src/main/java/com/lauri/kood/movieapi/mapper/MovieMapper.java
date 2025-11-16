package com.lauri.kood.movieapi.mapper;

import com.lauri.kood.movieapi.dto.ActorResponseDTO;
import com.lauri.kood.movieapi.dto.GenreResponseDTO;
import com.lauri.kood.movieapi.dto.MovieResponseDTO;
import com.lauri.kood.movieapi.entity.Movie;

import java.util.stream.Collectors;

public class MovieMapper {
    public static MovieResponseDTO toMovieResponseDTO(Movie movie) {
        return new MovieResponseDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseYear(),
                movie.getDuration(),
                movie.getActors()
                        .stream()
                        .map(actor -> new ActorResponseDTO(
                                actor.getId(),
                                actor.getName(),
                                actor.getBirthdate()))
                        .collect(Collectors.toSet()),

                movie.getGenres()
                        .stream()
                        .map(genre -> new GenreResponseDTO(
                                genre.getId(),
                                genre.getName()))
                        .collect(Collectors.toSet()
                        )
        );


    }
}
