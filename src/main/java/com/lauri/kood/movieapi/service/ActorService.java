package com.lauri.kood.movieapi.service;

import com.lauri.kood.movieapi.dto.ActorPatchDTO;
import com.lauri.kood.movieapi.dto.ActorResponseDTO;
import com.lauri.kood.movieapi.entity.Actor;
import com.lauri.kood.movieapi.exceptions.ResourceInUseException;
import com.lauri.kood.movieapi.exceptions.ResourceNotFoundException;
import com.lauri.kood.movieapi.mapper.ActorMapper;
import com.lauri.kood.movieapi.repository.ActorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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


    //create an actor with name and birthdate and return it to controller
    public ActorResponseDTO create(ActorPatchDTO actorDTO) {
        Actor actor = new Actor();
        actor.setName(actorDTO.name());
        actor.setBirthdate(actorDTO.birthdate());
        Actor savedActor = actorRepository.save(actor);
        return ActorMapper.toActorResponseDto(savedActor);
    }

    public Set<ActorResponseDTO> getAll() { //get all actors in a database, even if it is empty.
        return actorRepository.findAll()
                .stream()
                .map(actor -> new ActorResponseDTO(actor.getId(), actor.getName(), actor.getBirthdate()))
                .collect(Collectors.toSet());
    }

    public ActorResponseDTO findById(Long id) { //find actor by specific ID, else display an error
        Actor actor = actorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Actor with id " + id + " not found"));
        return ActorMapper.toActorResponseDto(actor);
    }

    public Set<ActorResponseDTO> filterByName(String name) { //search for a certain name in URL, else display an error.
        List<Actor> list = actorRepository.findByNameContainingIgnoreCase(name);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("name " + name + " not found in database.");
        }
        return list
                .stream()
                .map(ActorMapper::toActorResponseDto)
                .collect(Collectors.toSet());
    }

    @Transactional
    public ActorResponseDTO updateActor(Long id, ActorPatchDTO patch) {
        Actor actor = actorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Actor with id " + id + " not found"));
        if (patch.name() != null) { actor.setName(patch.name());} //we might check for blank too but that may be the intention of the modifier - to leave it blank.
        if (patch.birthdate() != null) {actor.setBirthdate(patch.birthdate()); }
        actorRepository.save(actor);//save new actor after patching fields.

        return ActorMapper.toActorResponseDto(actor);
    }

    @Transactional
    public void deleteActor(Long id, boolean force) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find actor with id: " + id));
        boolean hasRelations = !actor.getMovies().isEmpty();
        if (hasRelations && !force) {
            throw new ResourceInUseException("Cannot delete actor with related movies. Use ?force=true to override."); //Something is wrong here
        }
        if(force) {// If forced, clear the many-to-many relations properly
            actor.getMovies().forEach(movie -> movie.getActors().remove(actor));
            actor.getMovies().clear();
        }

        actorRepository.delete(actor);
        System.out.println("Deleted actor with id " + id + " which is " + actor.getName());
    }

}
