package com.lauri.kood.movieapi.controller;

import com.lauri.kood.movieapi.dto.ActorPatchDTO;
import com.lauri.kood.movieapi.dto.GenrePatchDTO;
import com.lauri.kood.movieapi.dto.GenrePostDTO;
import com.lauri.kood.movieapi.dto.GenreResponseDTO;
import com.lauri.kood.movieapi.entity.Genre;
import com.lauri.kood.movieapi.repository.GenreRepository;
import com.lauri.kood.movieapi.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RequestMapping(value = "/api/genres", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService, GenreRepository genreRepository) {
        this.genreService = genreService;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping//returns httpstatus 200 by default
    public Set<GenreResponseDTO> All() {
        return genreService.findAll();
    }

    @GetMapping("/{id}") //returns httpstatus 200 by default
    public GenreResponseDTO findById(@PathVariable Long id) {
        return genreService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED) //returns httpstatus 201
    @PostMapping
    public GenreResponseDTO create(@RequestBody @Validated GenrePatchDTO genreDto) {
        return genreService.create(genreDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT) //does not return anything after deletion, httpstatus 204
    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable Long id, @RequestParam(defaultValue = "false") Boolean force) {
        genreService.delete(id, force);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{id}") //should always update using ID but never expose ID to client.
    public void updateGenre(@PathVariable Long id, @RequestBody GenrePatchDTO genreDto) {
       genreService.updateGenre(id, genreDto);
    }

}
/*
Implement controller classes: GenreController, MovieController, and ActorController
Set up the following endpoints for each entity:


POST /api/{entity}: Create a new entity ----- DONE
GET /api/{entity}: Retrieve all entities  ----- DONE
GET /api/{entity}/{id}: Retrieve a specific entity by ID ----- DONE
DELETE /api/{entity}/{id}: Delete an entity ----- DONE
PATCH /api/{entity}/{id}: Partially update an existing entity ----- DONE


Additionally, implement filtering endpoints for the following:

GET /api/movies?genre={genreId}: Retrieve movies filtered by genre
GET /api/movies?year={releaseYear}: Retrieve movies filtered by release year
GET /api/movies?actor={Actor.id}: Retrieve movies that the actor with the given id has starred in
GET /api/movies/{movieId}/actors: Retrieve all actors starring in a movie
GET /api/actors?name={name}: Retrieve actors filtered by name
 */