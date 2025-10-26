package com.lauri.kood.movieapi.controller;

import com.lauri.kood.movieapi.entity.Actor;
import com.lauri.kood.movieapi.entity.Genre;
import com.lauri.kood.movieapi.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActorController {

    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/api/actor")
    List<Actor> All() {
        return actorService.findAll();
    }
}

