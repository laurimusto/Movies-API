package com.lauri.kood.movieapi.service;

import com.lauri.kood.movieapi.dto.MoviePostDTO;
import com.lauri.kood.movieapi.dto.MovieResponseDTO;
import com.lauri.kood.movieapi.entity.Actor;
import com.lauri.kood.movieapi.entity.Genre;
import com.lauri.kood.movieapi.entity.Movie;
import com.lauri.kood.movieapi.exceptions.ResourceNotFoundException;
import com.lauri.kood.movieapi.mapper.MovieMapper;
import com.lauri.kood.movieapi.repository.ActorRepository;
import com.lauri.kood.movieapi.repository.GenreRepository;
import com.lauri.kood.movieapi.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final GenreRepository genreRepository;

    public MovieService(MovieRepository movieRepository, ActorRepository actorRepository, GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.genreRepository = genreRepository;
    }

    public MovieResponseDTO createMovie(@RequestBody @Validated MoviePostDTO dto) { //@RequestBody can deserialize JSON into MoviePostDTO.
        Set<Actor> actors = new HashSet<>();
        for (Long id : dto.actors()) {
            Actor actor = actorRepository.findById(id) // attempts to retrieve the corresponding Actor entity from the ActorRepository using the findById method.
                    .orElseThrow(() -> new ResourceNotFoundException("Actor with id " + id + " not found"));
            actors.add(actor);
        }

        Set<Genre> genres = new HashSet<>();
        for (long id : dto.genres()) {
            Genre genre = genreRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Genre with id " + id + " not found"));
            genres.add(genre);
        }

        Movie movie = new Movie(dto.title(), dto.releaseYear(), dto.duration());
        movie.setActors(actors);
        movie.setGenres(genres);
        movieRepository.save(movie);
        return MovieMapper.toMovieResponseDTO(movie);
    }

    public MovieResponseDTO getMoviesById(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie with ID " + id + " not found"));
        return MovieMapper.toMovieResponseDTO(movie);
    }

    public Set<MovieResponseDTO> getAll() {
        System.out.println("this is getAll");

        return movieRepository.findAll().stream()
                .map(MovieMapper::toMovieResponseDTO)
                .collect(Collectors.toSet());
    }

    public MovieResponseDTO findByTitle(String title) {
        System.out.println("prints this");
        Movie movie = movieRepository.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new ResourceNotFoundException("Movie with title " + title + " not found"));
        return MovieMapper.toMovieResponseDTO(movie);
    }

    public MovieResponseDTO findByTitleLike(String title) {
        System.out.println("not this one");
        Movie movie = movieRepository.findByTitleLikeIgnoreCase(title)
                .orElseThrow(() -> new ResourceNotFoundException("Movie with title " + title + " not found"));
        return MovieMapper.toMovieResponseDTO(movie);

    }
}

