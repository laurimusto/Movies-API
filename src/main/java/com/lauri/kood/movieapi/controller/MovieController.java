package com.lauri.kood.movieapi.controller;

import com.lauri.kood.movieapi.PaginationValidator;
import com.lauri.kood.movieapi.dto.GenrePatchDTO;
import com.lauri.kood.movieapi.dto.MoviePatchDTO;
import com.lauri.kood.movieapi.dto.MoviePostDTO;
import com.lauri.kood.movieapi.dto.MovieResponseDTO;
import com.lauri.kood.movieapi.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<MovieResponseDTO> getAllMovies(
            @RequestParam(required = false) Long genre,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Long actor,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "99")int size) {
        PaginationValidator.validate(page, size);
        Pageable pageable = PageRequest.of(page, size);
        return movieService.getAllMovies(genre, year, actor, title, pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public MovieResponseDTO findById(@PathVariable Long id) { //@Pathvariable is for finding the path in URL
        return movieService.getMoviesById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")//should always update using ID but never expose ID to client.
    public void deleteMovie(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean force) {
        movieService.deleteMovie(id, force);
    }


    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")//should always update using ID but never expose ID to client.
    public MovieResponseDTO updateMovie(@PathVariable long id, @RequestBody MoviePatchDTO patchDTO) {
        return movieService.updateMovie(id, patchDTO);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public MovieResponseDTO createMovie(@RequestBody @Valid MoviePostDTO moviePost) {
        return movieService.createMovie(moviePost);
    }
}

/*
Implement controller classes: GenreController, MovieController, and ActorController
Set up the following endpoints for each entity:

POST /api/{entity}: Create a new entity ----- DONE
GET /api/{entity}: Retrieve all entities ----- DONE
GET /api/{entity}/{id}: Retrieve a specific entity by ID ----- DONE

DELETE /api/{entity}/{id}: Delete an entity
Deleting a genre that has associated movies, the response should indicate that the operation can't be completed:
Cannot delete genre 'Action' because it has 15 associated movies

validate year
validate duration


PATCH /api/{entity}/{id}: Partially update an existing entity
update actors or genres

Additionally, implement filtering endpoints for the following:

GET /api/movies?genre={genreId}: Retrieve movies filtered by genre ----- DONE
GET /api/movies?year={releaseYear}: Retrieve movies filtered by release year ----- DONE
GET /api/movies?actor={Actor.id}: Retrieve movies that the actor with the given id has starred in ----- DONE
GET /api/movies/{movieId}/actors: Retrieve all actors starring in a movie
GET /api/actors?name={name}: Retrieve actors filtered by name
 */
