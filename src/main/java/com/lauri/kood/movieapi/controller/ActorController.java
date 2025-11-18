package com.lauri.kood.movieapi.controller;

import com.lauri.kood.movieapi.dto.ActorPatchDTO;
import com.lauri.kood.movieapi.dto.ActorResponseDTO;
import com.lauri.kood.movieapi.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
/*
@RequestMapping
    That ensures clients (like browsers or REST tools) know they’re receiving
    JSON data — and not HTML, XML, or plain text.

 */
import java.util.List;
import java.util.Set;

@RequestMapping(value = "/api/actors", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ActorController {

    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping //get every actor from a database.
    public Set<ActorResponseDTO> getAll() {
        return actorService.getAll();
    }

    @GetMapping("/{id}")//get Actor with corresponding ID from a database.
    public ActorResponseDTO findById(@PathVariable Long id) {
        return actorService.findById(id);
    }
    
    @GetMapping("/search") //Search for a certain user in url using /search?name=Leonardo%20Dicaprio
    public Set<ActorResponseDTO> filterByName(@PathVariable String name) {
    return actorService.filterByName(name);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping ("/{id}")//create a new listing in a database with new ID
    public ActorResponseDTO createActor(@RequestBody @Validated ActorPatchDTO actorDto) {
      return actorService.create(actorDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")//should always update using ID but never expose ID to client.
    public void deleteActor(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean force) {
        actorService.deleteActor(id, force);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{id}")//should always update using ID but never expose ID to client.
    public void updateActor(@PathVariable Long id, @RequestBody ActorPatchDTO patch) {
        actorService.updateActor(id, patch);
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

PATCH /api/{entity}/{id}: Partially update an existing entity

Additionally, implement filtering endpoints for the following:

GET /api/movies?genre={genreId}: Retrieve movies filtered by genre
GET /api/movies?year={releaseYear}: Retrieve movies filtered by release year
GET /api/movies?actor={Actor.id}: Retrieve movies that the actor with the given id has starred in
GET /api/movies/{movieId}/actors: Retrieve all actors starring in a movie
GET /api/actors?name={name}: Retrieve actors filtered by name
 */

