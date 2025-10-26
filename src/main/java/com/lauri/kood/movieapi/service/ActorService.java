package com.lauri.kood.movieapi.service;

import com.lauri.kood.movieapi.entity.Actor;
import com.lauri.kood.movieapi.entity.Genre;
import com.lauri.kood.movieapi.repository.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ActorService {

    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }


    public Actor create(Actor actor) {
        return actorRepository.save(actor);
    }

    public Actor findById(Long id) {
        return actorRepository.findById(id).orElse(null); //retrieve actor by id
    }


    public List<Actor> findAll() {
        return actorRepository.findAll(); //retrieve all actors from database

    }
}
