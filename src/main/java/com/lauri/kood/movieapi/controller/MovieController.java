package com.lauri.kood.movieapi.controller;

import com.lauri.kood.movieapi.dto.MoviePatchDTO;
import com.lauri.kood.movieapi.dto.MoviePostDTO;
import com.lauri.kood.movieapi.dto.MovieResponseDTO;
import com.lauri.kood.movieapi.service.MovieService;
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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public MovieResponseDTO createMovie(@RequestBody @Validated MoviePostDTO moviePost){
        return movieService.createMovie(moviePost);

    }

    @GetMapping
    public Set<MovieResponseDTO> findByTitle(@RequestParam(required = false) @Validated String title) {
        if(title != null && !title.isBlank()) {
            MovieResponseDTO movie = movieService.findByTitle(title);
            return Set.of(movie);
        }
    return movieService.getAll();
    }

    @GetMapping("/{id}")
    public MovieResponseDTO findById(@PathVariable Long id) { //@Pathvariable is for finding the path in URL
       return movieService.getMoviesById(id);
    }


}

/*
Implement controller classes: GenreController, MovieController, and ActorController
Set up the following endpoints for each entity:

POST /api/{entity}: Create a new entity ----- DONE
GET /api/{entity}: Retrieve all entities ----- DONE
GET /api/{entity}/{id}: Retrieve a specific entity by ID ----- DONE

PATCH /api/{entity}/{id}: Partially update an existing entity
DELETE /api/{entity}/{id}: Delete an entity
Additionally, implement filtering endpoints for the following:

GET /api/movies?genre={genreId}: Retrieve movies filtered by genre
GET /api/movies?year={releaseYear}: Retrieve movies filtered by release year
GET /api/movies?actor={Actor.id}: Retrieve movies that the actor with the given id has starred in
GET /api/movies/{movieId}/actors: Retrieve all actors starring in a movie
GET /api/actors?name={name}: Retrieve actors filtered by name
 */
