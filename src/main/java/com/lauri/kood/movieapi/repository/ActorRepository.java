package com.lauri.kood.movieapi.repository;

import com.lauri.kood.movieapi.entity.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ActorRepository extends JpaRepository<Actor, Long> {

    // Returns a paginated list of actors whose names contain the given string (case-insensitive)
    Page<Actor> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Returns a paginated list of actors who appeared in the movie with the specified ID
    Page<Actor> findByMovies_Id(Long movieId, Pageable pageable);
}
