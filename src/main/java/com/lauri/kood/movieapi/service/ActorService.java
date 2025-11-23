package com.lauri.kood.movieapi.service;

import com.lauri.kood.movieapi.dto.ActorPatchDTO;
import com.lauri.kood.movieapi.dto.ActorResponseDTO;
import com.lauri.kood.movieapi.dto.MovieResponseDTO;
import com.lauri.kood.movieapi.entity.Actor;
import com.lauri.kood.movieapi.entity.Movie;
import com.lauri.kood.movieapi.exceptions.ResourceInUseException;
import com.lauri.kood.movieapi.exceptions.ResourceNotFoundException;
import com.lauri.kood.movieapi.mapper.ActorMapper;
import com.lauri.kood.movieapi.mapper.MovieMapper;
import com.lauri.kood.movieapi.repository.ActorRepository;
import com.lauri.kood.movieapi.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final MovieRepository movieRepository;

    public ActorService(ActorRepository actorRepository, MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
    }


    //create an actor with name and birthdate and return it to controller
    @Transactional
    public ActorResponseDTO create(ActorPatchDTO actorDTO) {
        Actor actor = new Actor();
        actor.setName(actorDTO.name());
        actor.setBirthdate(actorDTO.birthdate());
        Actor savedActor = actorRepository.save(actor);
        return ActorMapper.toActorResponseDto(savedActor);
    }

    // Retrieve all actors from the database, also can use name filter
    public Page<ActorResponseDTO> getAll(String name, Pageable pageable) {
        if (name != null && !name.isEmpty()) {
            return actorRepository.findByNameContainingIgnoreCase(name, pageable)
                    .map(ActorMapper::toActorResponseDto);
        }
        return actorRepository.findAll(pageable)
                .map(ActorMapper::toActorResponseDto);

    }

    public ActorResponseDTO findById(Long id) { //find actor by specific ID, else display an error
        Actor actor = actorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Actor with id " + id + " not found"));
        return ActorMapper.toActorResponseDto(actor);
    }

    //Filter actor by name
    public Page<ActorResponseDTO> getName(String name, Pageable pageable) {
        return actorRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(ActorMapper::toActorResponseDto);
    }

    public Page<MovieResponseDTO> getMoviesByActor(Long id, Pageable pageable) {
        Actor actor = actorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Actor with id " + id + " not found"));
        return movieRepository.findByActors_Id(id, pageable)
                .map(MovieMapper::toMovieResponseDTO);
    }

    @Transactional
    public ActorResponseDTO updateActor(Long id, ActorPatchDTO patch) {
        Actor actor = actorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Actor with id " + id + " not found"));
        if (patch.name() != null) {
            actor.setName(patch.name());
        } //we might check for blank too but that may be the intention of the modifier - to leave it blank. isBlank() for that.
        if (patch.birthdate() != null) {
            actor.setBirthdate(patch.birthdate());
        }

        //for removing movies
        if (patch.removeMovieIds() != null && !patch.removeMovieIds().isEmpty()) {
            List<Movie> moviesToRemove = movieRepository.findAllById(patch.removeMovieIds());
            if (moviesToRemove.size() != patch.removeMovieIds().size()) {
                throw new ResourceNotFoundException("One ore more movies not found");
            }
            moviesToRemove.forEach(movie -> {
                actor.getMovies().remove(movie);
                movie.getActors().remove(actor);
            });
        }

        //for adding movies
        if (patch.addMovieIds() != null && !patch.addMovieIds().isEmpty()) {
            List<Movie> moviesToAdd = movieRepository.findAllById(patch.addMovieIds());
            if(moviesToAdd.size() != patch.addMovieIds().size()) {
                throw new ResourceNotFoundException("one or more movies not found!");
            }
            moviesToAdd.forEach(movie -> {
                actor.getMovies().add(movie);
                movie.getActors().add(actor);
            });
        }
        return ActorMapper.toActorResponseDto(actorRepository.save(actor));
    }

    @Transactional
    public void deleteActor(Long id, boolean force) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find actor with id: " + id));
        boolean hasRelations = !actor.getMovies().isEmpty();
        if (hasRelations && !force) {
            // If movies are found, block the deletion and throw an error
            throw new ResourceInUseException(
                    "Cannot delete actor '" + actor.getName() + "' as they are in " +
                            actor.getMovies().size() + " movies. Use '?force=true' to override."
            );
        }
        if (force) {// If forced, clear the many-to-many relations properly
            actor.getMovies().forEach(movie -> movie.getActors().remove(actor));
            actor.getMovies().clear();
        }

        actorRepository.delete(actor);
        System.out.println("Deleted actor with id " + id + " which is " + actor.getName());
    }

}
