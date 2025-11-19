package com.lauri.kood.movieapi.service;

import com.lauri.kood.movieapi.dto.MoviePatchDTO;
import com.lauri.kood.movieapi.dto.MoviePostDTO;
import com.lauri.kood.movieapi.dto.MovieResponseDTO;
import com.lauri.kood.movieapi.entity.Actor;
import com.lauri.kood.movieapi.entity.Genre;
import com.lauri.kood.movieapi.entity.Movie;
import com.lauri.kood.movieapi.exceptions.InvalidDurationException;
import com.lauri.kood.movieapi.exceptions.InvalidReleaseYearException;
import com.lauri.kood.movieapi.exceptions.ResourceInUseException;
import com.lauri.kood.movieapi.exceptions.ResourceNotFoundException;
import com.lauri.kood.movieapi.mapper.MovieMapper;
import com.lauri.kood.movieapi.repository.ActorRepository;
import com.lauri.kood.movieapi.repository.GenreRepository;
import com.lauri.kood.movieapi.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

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

    @Transactional
    public void deleteMovie(Long id, boolean force) {
        Movie movie = movieRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie with id -" + id + "- not found"));
        boolean hasRelations = !movie.getActors().isEmpty() || !movie.getGenres().isEmpty();
        if (hasRelations && !force) {
            throw new ResourceInUseException("Cannot delete movie with related genres or actors. Use ?force=true to override.");
        }
        if (force) {// If forced, clear the many-to-many relations properly
            movie.getActors().forEach(actor -> actor.getMovies().remove(movie)); //delete the movie from every actors list which appear in current movie.
            movie.getActors().clear(); //delete movies own list of actors
            movie.getGenres().forEach(genre -> genre.getMovies().remove(movie));
            movie.getActors().clear();
        }
        movieRepository.delete(movie);
        System.out.println("Deleted actor with id " + id + " which is " + movie.getTitle());
    }

    @Transactional
    public MovieResponseDTO updateMovie(Long id, MoviePatchDTO patch) {
        Movie movie = movieRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie with id -" + id + "- not found"));
        if (patch.title() != null)
            movie.setTitle(patch.title());//we might check for blank too but that may be the intention of the modifier - to leave it blank. otherwise we can use .isBlank()

        if (patch.duration() != null) { //runtime cannot be negative number.
            validateDuration(patch.duration());
            movie.setDuration(patch.duration());
        }

        if (patch.releaseYear() != null) { //release year must be between 1888 and now.
            validateYear(patch.releaseYear());
            movie.setReleaseYear(patch.releaseYear());
        }
        return MovieMapper.toMovieResponseDTO(movie);
    }

    private void validateYear(Integer year) {
        int currentYear = java.time.Year.now().getValue();
        if (year < 1888 || year > currentYear) {
            throw new InvalidReleaseYearException("Year " + year + " is invalid release year. Must be between 1888 and " + currentYear);
        }
    }

    private void validateDuration(Integer duration) {
        if (duration <= 0) {
            throw new InvalidDurationException("Duration must be a positive number");
        }
    }

    //placeholder for handling associations of movie-genre, movie-actor

    //placeholder
}

