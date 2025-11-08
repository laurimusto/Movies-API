package com.lauri.kood.movieapi.controller;

import com.lauri.kood.movieapi.dto.ActorPatchDTO;
import com.lauri.kood.movieapi.dto.ActorRequestDTO;
import com.lauri.kood.movieapi.dto.ActorResponseDTO;
import com.lauri.kood.movieapi.entity.Actor;
import com.lauri.kood.movieapi.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
/*
@RequestMapping
    That ensures clients (like browsers or REST tools) know they’re receiving
    JSON data — and not HTML, XML, or plain text.

 */
import java.util.List;

@RequestMapping(value = "/api/actors", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ActorController {

    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping //get every actor from database.
    public List<ActorResponseDTO> getAll() {
        return actorService.getAll();
    }

    @GetMapping("/{id}")//get Actor with corresponding ID from databse.
    public ActorResponseDTO getId(@PathVariable Long id) {
        return actorService.findById(id);
    }
    
    @GetMapping("/search") //Search for certain user in url using /search?name=Leonardo%20Dicaprio
    public List<ActorResponseDTO> filterbyName(@PathVariable String name) {
    return actorService.filterByName(name);
    }

    @PostMapping //create a new listing in database with new ID
    public ActorResponseDTO createActor(@RequestBody @Validated ActorPatchDTO actorDto) {
      return actorService.create(actorDto);
    }

}

