package com.lauri.kood.movieapi.service;

import com.lauri.kood.movieapi.dto.ActorResponseDTO;
import com.lauri.kood.movieapi.dto.ActorRequestDTO;
import com.lauri.kood.movieapi.entity.Actor;
import com.lauri.kood.movieapi.repository.ActorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
/*
Actor
The actor service should handle adding new actors with their name and birth date.

    Implement methods to retrieve all actors,
    get a specific actor by ID, &&
and filter actors by name.
You'll also need a way to fetch all movies an actor has appeared in.
Lastly, ensure you can modify an existing actor's details
(including their name,
 birth date,
and associated movies)
and remove an actor from the database.

For the purpose of this exercise, use the PATCH method for updating entities instead of PUT.
PATCH allows for partial updates to a resource,
whereas PUT typically requires sending the complete updated resource.
 */

@Service
public class ActorService {

    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    private ActorResponseDTO toResponse(Actor actor) {
        return new ActorResponseDTO(actor.getId(), actor.getName(), actor.getBirthdate());
    }

    public ActorResponseDTO create(ActorRequestDTO request) { //create an actor with name and birthdate
        Actor actor = new Actor();
        actor.setName(request.name());
        actor.setBirthdate(LocalDate.parse(request.birthdate()));
        Actor saved = actorRepository.save(actor);
        return toResponse(saved);
    }

    public List<ActorResponseDTO> getAll() {
        return actorRepository.findAll()
                .stream()
                .map(actor -> new ActorResponseDTO(actor.getId(), actor.getName(), actor.getBirthdate()))
                .toList();
    }

    public ActorResponseDTO findById(Long id) { //find actor by specific ID
        Actor actor = actorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Actor with id " + id + " not found"));
        return toResponse(actor);
    }



}
