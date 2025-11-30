package com.lauri.kood.movieapi.controller;

import com.lauri.kood.movieapi.PaginationValidator;
import com.lauri.kood.movieapi.dto.ActorPatchDTO;
import com.lauri.kood.movieapi.dto.ActorPostDTO;
import com.lauri.kood.movieapi.dto.ActorResponseDTO;
import com.lauri.kood.movieapi.dto.MovieResponseDTO;
import com.lauri.kood.movieapi.service.ActorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
/*
@RequestMapping
    That ensures clients (like browsers or REST tools) know they’re receiving
    JSON data — and not HTML, XML, or plain text.

 */

@RequestMapping(value = "/api/actors", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ActorController {

    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping //get every actor from a database and also apply filters if needed.
    public Page<ActorResponseDTO> getAll(@RequestParam(required = false) String name,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "99") int size,
                                         @RequestParam(required = false) String sortBy,
                                         @RequestParam(required = false, defaultValue = "true") boolean ascending) {
        //if ascending is true, then sort by ascending, otherwise sort by descending order.
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            PaginationValidator.validate(page, size);
            Pageable pageable = PageRequest.of(page, size, sort);
            return actorService.getAll(name, pageable);
        }

        @GetMapping("/search") //enables searching by keyword
        public Page<ActorResponseDTO> searchByName (@RequestParam String name,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "99") int size){

            PaginationValidator.validate(page, size);
            Pageable pageable = PageRequest.of(page, size);
            return actorService.getName(name, pageable);
        }

        @GetMapping("/{id}")//get Actor with corresponding ID from a database.
        public ActorResponseDTO findById (@PathVariable Long id){
            return actorService.findById(id);
        }

        @GetMapping("/{id}/movies") //return all movies where actor has appeared
        public Page<MovieResponseDTO> getMoviesByActor (@PathVariable Long id,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "99") int size){
            PaginationValidator.validate(page, size);
            Pageable pageable = PageRequest.of(page, size);
            return actorService.getMoviesByActor(id, pageable);
        }

        @ResponseStatus(HttpStatus.CREATED)
        @PostMapping//create a new listing in a database with new ID
        public ActorResponseDTO createActor (@RequestBody @Valid ActorPostDTO actorDto){
            return actorService.create(actorDto);
        }


        @ResponseStatus(HttpStatus.OK)
        @PatchMapping("/{id}")//should always update using ID but never expose ID to client.
        public ActorResponseDTO updateActor (@PathVariable Long id, @Valid @RequestBody ActorPatchDTO patch){
            return actorService.updateActor(id, patch);
        }
        @Transactional
        @ResponseStatus(HttpStatus.NO_CONTENT)
        @DeleteMapping("/{id}")//should always update using ID but never expose ID to client.
        public void deleteActor (@PathVariable Long id,@RequestParam(defaultValue = "false") boolean force){
            actorService.deleteActor(id, force);
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

