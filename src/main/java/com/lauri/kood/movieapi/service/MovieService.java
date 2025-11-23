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
import jakarta.persistence.criteria.Join;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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
        Movie movie = new Movie();
        movie.setTitle(dto.title());
        movie.setReleaseYear(dto.releaseYear());
        movie.setDuration(dto.duration());


        if (dto.genres() != null && !dto.genres().isEmpty()) {
            List<Genre> genres = genreRepository.findAllById(dto.genres());
            if (genres.size() != dto.genres().size()) {
                throw new ResourceNotFoundException("One or more genres not found");
            }
            movie.getGenres().addAll(genres);
        }

        if (dto.actors() != null && !dto.actors().isEmpty()) {
            List<Actor> actors = actorRepository.findAllById(dto.actors());
            if (actors.size() != dto.actors().size()) {
                throw new ResourceNotFoundException("One or more actors not found");
            }
            movie.getActors().addAll(actors);
        }
        return MovieMapper.toMovieResponseDTO(movieRepository.save(movie));
    }

    public MovieResponseDTO getMoviesById(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie with ID " + id + " not found"));
        return MovieMapper.toMovieResponseDTO(movie);
    }

    /*
    if NO filters are provided in GET, it returns a full list of movies.
    cb is CriteriaBuilder
    Start with all movies.
    If genreId is provided - narrow down movies by genre.
    If releaseYear is provided - narrow down movies by year.
    If actorId is provided - narrow down movies by actor.
    If title is provided - narrow down movies by title substring.
    Apply paging and sorting using Pageable.
    Convert entities to DTOs - return Page<MovieResponseDTO>.
     */
    public Page<MovieResponseDTO> getAllMovies(
            Long genreId,
            Integer releaseYear,
            Long actorId,
            String title,
            Pageable pageable) {

        Specification<Movie> spec = Specification.unrestricted();

        if (genreId != null) {  //only applies if genreId is provided
            spec = spec.and((movie, query, criteria) -> {
                System.out.println("Filtering movie: " + movie.get("title")); //Root is a starting table(movie),
                Join<Movie, Genre> genreJoin = movie.join("genres"); //Joins movie and genre(SQL JOIN) to see each movie's genres
                return criteria.equal(genreJoin.get("id"), genreId); //generates SQL WHERE genre.id = ? Keep only movies that have a genre matching genreId
            });
        }
        if (releaseYear != null) {
            spec = spec.and((movie, query, criteria) -> //This line adds a filter to the query that only selects movies whose releaseYear matches the value provided.
                    criteria.equal(movie.get("releaseYear"), releaseYear)); //no join needed because its in movie table.
        }
        if (actorId != null) {
            spec = spec.and((movie, query, criteria) -> {
                Join<Movie, Actor> actorJoin = movie.join("actors");
                return criteria.equal(actorJoin.get("id"), actorId);
            });

        }
        if (title != null && !title.isBlank()) {
            spec = spec.and((movie, query, criteria) ->
                    criteria.like(criteria.lower(movie.get("title")), "%" + title.toLowerCase() + "%")); //Case-insensitive partial match: SQL LIKE "%keyword%",% is wildcard, meaning “anything before or after”
        }

        return movieRepository.findAll(spec, pageable)
                .map(MovieMapper::toMovieResponseDTO);

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
            throw new ResourceInUseException(String.format(
                    "Cannot delete movie [%s] because it has [%d] actors and [%d] genres",
                    movie.getTitle(),
                    movie.getActors().size(),
                    movie.getGenres().size()
            ));
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

    @Transactional
    public MovieResponseDTO filterMovies(Long genreId) {
        Movie movie = movieRepository.findByGenresId(genreId).stream().findFirst().orElseThrow(() -> new ResourceNotFoundException("No movies found for genre ID  " + genreId));
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

