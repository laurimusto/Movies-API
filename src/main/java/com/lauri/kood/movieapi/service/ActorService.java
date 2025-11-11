package com.lauri.kood.movieapi.service;
import com.lauri.kood.movieapi.dto.ActorPatchDTO;
import com.lauri.kood.movieapi.dto.ActorResponseDTO;
import com.lauri.kood.movieapi.entity.Actor;
import com.lauri.kood.movieapi.exceptions.ResourceNotFoundException;
import com.lauri.kood.movieapi.repository.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
/*
Actor
The actor service should handle adding new actors with their name and birth date.

    Implement methods to retrieve all actors,
    get a specific actor by ID, &&
    and filter actors by name.
You'll also need a way to fetch all movies an actor has appeared in.
Lastly, ensure you can modify an existing actor's details
(including their name,
 birthdate,
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
    //create an actor with name and birthdate and return it to controller
    public ActorResponseDTO create(ActorPatchDTO actorDTO) {
        Actor actor = new Actor();
        actor.setName(actorDTO.name());
        actor.setBirthdate(actorDTO.birthdate());
        Actor savedActor = actorRepository.save(actor);
        return toResponse(savedActor);
    }

    public List<ActorResponseDTO> getAll() { //get all actors in a database, even if it is empty.
        return actorRepository.findAll()
                .stream()
                .map(actor -> new ActorResponseDTO(actor.getId(), actor.getName(), actor.getBirthdate()))
                .toList();
    }

    public ActorResponseDTO findById(Long id) { //find actor by specific ID, else display an error
        Actor actor = actorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Actor with id " + id + " not found"));
        return toResponse(actor);
    }

    public List<ActorResponseDTO> filterByName(String name) { //search for a certain name in URL, else display an error.
    List<Actor> list = actorRepository.findByNameContainingIgnoreCase(name);
    if(list.isEmpty()) {
        throw new ResourceNotFoundException("name " + name + " not found in database.");
    } return list
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ActorResponseDTO patchName(Long id, ActorPatchDTO patch) {
        Actor actor = actorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Actor with id " + id + " not found"));
        if(patch.name() != null) { actor.setName(patch.name()); }
        if(patch.birthdate() != null) { actor.setBirthdate(patch.birthdate()); }
        actorRepository.save(actor);

        return toResponse(actor);
    }

}
