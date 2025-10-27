package com.lauri.kood.movieapi.controller;

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

    private final ActorService service;

    @Autowired
    public ActorController(ActorService actorService) {
        this.service = actorService;
    }

    @GetMapping
    List<ActorResponseDTO> All() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ActorResponseDTO get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ActorResponseDTO create(@RequestBody @Validated Actor actor) {
        return service.create(actor);
    }
}

